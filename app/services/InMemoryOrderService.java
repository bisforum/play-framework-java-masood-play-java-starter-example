package services;

import com.google.inject.Module;
import org.joda.time.DateTime;

import javax.inject.*;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


/**
 * This class is a concrete implementation of the {@link Order} trait.
 * It is configured for Guice dependency injection in the {@link Module}
 * class.
 * <p>
 * This class has a {@link Singleton} annotation because we need to make
 * sure we only use one counter per application. Without this
 * annotation we would get a new instance every time a {@link Order} is
 * injected.
 */
@Singleton
public class InMemoryOrderService implements Order {
    //    private final AtomicInteger atomicCounter = new AtomicInteger();
//    private Configuration configuration;

//    @Inject
//    public InMemoryOrderService(Configuration configuration) {
//        this.configuration = configuration;
//    }

    private int sum = 0;
    private int average = 0;

    @Override
    public CompletionStage<Void> addOrder(Transaction transaction) {

        return dequeueOldOrders(transaction.timeStamp).thenAccept(updatedQueue ->
        {
            orderQueue.add(transaction);
            sum = sum + transaction.amount;
            average = sum / orderQueue.size();
        });

//        return CompletableFuture.runAsync(() -> {
//            dequeueOldOrders(transaction.timeStamp);
////            return null;
//        }).thenAccept(any -> {
//            orderQueue.add(transaction);
//            sum = sum + transaction.amount;
//            average = sum / orderQueue.size();
//        });
    }

    @Override
    public CompletionStage<Statistics> getStatistics(Date requestTime) {
        return dequeueOldOrders(requestTime).thenApply(updatedQueue -> new Statistics(sum, average));
    }

    private CompletableFuture<Void> dequeueOldOrders(Date requestTime) {

        return CompletableFuture.runAsync(() -> {
            boolean olderThanOneMinute = true;
            while (olderThanOneMinute && !orderQueue.isEmpty()) {
                DateTime timeWindowStart = new DateTime(requestTime).minusSeconds(60);
                DateTime transactionTime = new DateTime(orderQueue.peek().timeStamp);

                if (transactionTime.isBefore(timeWindowStart) && !orderQueue.isEmpty()) {
                    int headAmount = orderQueue.peek().amount;
                    sum = sum - headAmount;
                    System.out.println("Sum is : " + sum);
                    System.out.println("Avg is : " + average);
                    average = sum / orderQueue.size();
                    orderQueue.remove();
                } else {
                    System.out.println("Order size is: " + orderQueue.size());
                    System.out.println("Order top is: " + orderQueue.peek().timeStamp);
                    olderThanOneMinute = false;
                }
            }
        });
    }

}
