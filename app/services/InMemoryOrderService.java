package services;

import org.joda.time.DateTime;
import java.util.Queue;
import com.typesafe.config.Config;
import play.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryOrderService implements OrderService {


    private final BlockingQueue<Transaction> orderQueue;
    private Config config;

    @javax.inject.Inject
    public InMemoryOrderService(Config config, BlockingQueue<Transaction> orderQueue) {
        this.config = config;
        this.orderQueue = orderQueue;
    }

    AtomicInteger atomicSum = new AtomicInteger(0);

    //    private final int timeWindow = (int) config.getDuration("statistics.time.window", TimeUnit.SECONDS);
    private final int timeWindow = 60000;


    @Override
    public CompletionStage<Void> addOrder(Transaction transaction) {
        Logger.debug("The following transaction is going to be added to the queue (amount , timestampt): " + transaction.amount + " , " + transaction.timeStamp);

        return CompletableFuture.runAsync(() -> {
            orderQueue.add(transaction);
            atomicSum.set(atomicSum.get() + transaction.amount);
        });
    }

    @Override
    public CompletionStage<Statistics> getStatistics(DateTime requestTime) {
        Logger.debug("Entered " + this.getClass().getSimpleName() + ".getStatistics method; remoteAddress=");

        return dequeueOldOrders(requestTime).thenApply(updatedQueue ->
        {
            if (orderQueue.isEmpty()) {
                return new Statistics(0, (0));

            } else {
                return new Statistics(atomicSum.get(), (atomicSum.get() / orderQueue.size()));
            }
        });
    }


    public CompletableFuture<Void> dequeueOldOrders(DateTime requestTime) {

        return CompletableFuture.runAsync(() -> {
            boolean olderThanOneMinute = true;
            Logger.debug("queue size is: " + orderQueue.size());
            while (olderThanOneMinute && !orderQueue.isEmpty()) {

                DateTime timeWindowStart = (requestTime).minusMillis((timeWindow));
                DateTime transactionTime = (orderQueue.peek().timeStamp);

                if (transactionTime.isBefore(timeWindowStart)) {
                    Logger.debug("The following transaction is about tho be removed form the queue" + orderQueue.peek());
                    atomicSum.set(atomicSum.get() - orderQueue.poll().amount);
                } else {
                    olderThanOneMinute = false;
                }
            }
        });
    }

}
