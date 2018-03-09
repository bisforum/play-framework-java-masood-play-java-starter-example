package services;

import org.joda.time.DateTime;

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

    private AtomicInteger atomicSum = new AtomicInteger(0);

    //    Todo: Read the timeWindow from config files to make the app and tests more configurable
    //        private final int timeWindow = Integer.parseInt(config.getString("statistics.time.window"));
    private final int timeWindow = 60000;

    @Override
    public CompletionStage<Void> addOrder(Transaction transaction) {
        Logger.debug("Entering " + this.getClass().getSimpleName() + ".addOrder method with transaction: " + transaction.timeStamp + ", " + transaction.amount);
        Logger.debug("The following transaction is going to be added to the queue (amount , timestamp): " + transaction.amount + " , " + transaction.timeStamp);

        return CompletableFuture.runAsync(() -> {
            orderQueue.add(transaction);
            atomicSum.set(atomicSum.get() + transaction.amount);
        });
    }

    @Override
    public CompletionStage<Statistics> getStatistics(DateTime requestTime) {
        Logger.debug("Entering " + this.getClass().getSimpleName() + ".getStatistics method with requestTime " + requestTime.toString());

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
