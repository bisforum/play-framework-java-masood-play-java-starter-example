package services;

import com.google.inject.Module;
import org.joda.time.DateTime;
import play.Configuration;

import javax.inject.*;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;


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


//    @Inject
//    Configuration configuration = Play.current().injector().instanceOf(Configuration.class);

//    private Config config;
//
//    @javax.inject.Inject
//    public InMemoryOrderService(Config config) {
//        this.config = config;
//    }

    static private int sum = 0;
    static private int average = 0;

    //    private final int timeWindow = (int) config.getDuration("statistics.time.window", TimeUnit.SECONDS);
//    private final int timeWindow = Integer.valueOf(Configuration.root().getString("statistics.time.window"));
    private final int timeWindow = 60;


    @Override
    public CompletionStage<Void> addOrder(Transaction transaction) {

        return dequeueOldOrders(transaction.timeStamp).thenAccept(updatedQueue ->
        {
//            int updatedSum = orderQueue.peek().sum.orElse(0) + transaction.amount;
//            transaction.sum = Optional.of(updatedSum);
            orderQueue.add(transaction);
//            sum = sum + transaction.amount;
//            average = sum / orderQueue.size();
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

    @Override
    public CompletionStage<Statistics> computeSum() {


        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (Transaction transaction : orderQueue) {
                sum = sum + transaction.amount;
            }
            int average = sum / orderQueue.size();
            return new Statistics(sum, average);
        });


    }

    private CompletableFuture<Void> dequeueOldOrders(Date requestTime) {
//        System.out.println(">>>>>>" + timeWindow);
        return CompletableFuture.runAsync(() -> {
            boolean olderThanOneMinute = true;
            int newSum = 0;
            while (olderThanOneMinute && !orderQueue.isEmpty()) {
                DateTime timeWindowStart = new DateTime(requestTime).minusMillis((timeWindow));
                DateTime transactionTime = new DateTime(orderQueue.peek().timeStamp);

                if (transactionTime.isBefore(timeWindowStart) && !orderQueue.isEmpty()) {
//                    int headAmount = orderQueue.peek().amount;
//                    int headSum = orderQueue.peek().sum.get();
//                    newSum = headSum - headAmount;
//                    System.out.println("Sum is : " + sum);
//                    System.out.println("Avg is : " + average);
//                    average = sum / orderQueue.size();
                    orderQueue.remove();
//                    orderQueue.peek().sum = orderQueue.peek().sum;
                } else {
//                    System.out.println("Order size is: " + orderQueue.size());
//                    System.out.println("Order top is: " + orderQueue.peek().timeStamp);
                    olderThanOneMinute = false;
                }
            }
        });
    }

}
