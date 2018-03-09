package services;

import com.google.inject.Module;
import org.joda.time.DateTime;
import play.Configuration;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryOrderService implements Order {


//    private Config config;
//
//    @javax.inject.Inject
//    public InMemoryOrderService(Config config) {
//        this.config = config;
//    }

    AtomicInteger atomicSum = new AtomicInteger(0);

    //    private final int timeWindow = (int) config.getDuration("statistics.time.window", TimeUnit.SECONDS);
    private final int timeWindow = 60000;


    @Override
    public CompletionStage<Void> addOrder(Transaction transaction) {

        return CompletableFuture.runAsync(() -> {
            orderQueue.add(transaction);
            atomicSum.set(atomicSum.get() + transaction.amount);
        });
    }

    @Override
    public CompletionStage<Statistics> getStatistics(Date requestTime) {
        return dequeueOldOrders(requestTime).thenApply(updatedQueue ->
        {
            if (orderQueue.isEmpty()) {
                return new Statistics(0, (0));

            } else {
                return new Statistics(atomicSum.get(), (atomicSum.get() / orderQueue.size()));
            }
        });
    }


    public CompletableFuture<Void> dequeueOldOrders(Date requestTime) {

        return CompletableFuture.runAsync(() -> {
            boolean olderThanOneMinute = true;

            while (olderThanOneMinute && !orderQueue.isEmpty()) {
                DateTime timeWindowStart = new DateTime(requestTime).minusMillis((timeWindow));
                DateTime transactionTime = new DateTime(orderQueue.peek().timeStamp);

                if (transactionTime.isBefore(timeWindowStart) && !orderQueue.isEmpty()) {
                    atomicSum.set(atomicSum.get() - orderQueue.poll().amount);
                } else {
                    olderThanOneMinute = false;
                }
            }
        });
    }

}
