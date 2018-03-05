package services;

import org.joda.time.DateTime;

import javax.inject.*;
import java.util.Date;

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


    @Override
    public void addOrder(Transaction transaction) {
        dequeueOldOrders(transaction.timeStamp);
        orderQueue.add(transaction);
    }

    @Override
    public Statistics getStatistics(Date requestTime) {
        int average, sum = 0;
        dequeueOldOrders(requestTime);
        if (!orderQueue.isEmpty()) {
            for (Transaction transaction : orderQueue) {
                System.out.println(transaction.amount);
                sum = transaction.amount + sum;
            }
            average = sum / orderQueue.size();
            return new Statistics(sum, average);
        } else {
            return new Statistics(0, 0);

        }
    }

    private void dequeueOldOrders(Date requestTime) {

        boolean olderThanOneMinute = true;

        while (olderThanOneMinute && !orderQueue.isEmpty()) {
            DateTime timeWindowStart = new DateTime(requestTime).minusMinutes(1);
            DateTime transactionTime = new DateTime(orderQueue.peek().timeStamp);

            if (transactionTime.isBefore(timeWindowStart) && !orderQueue.isEmpty()) {
                orderQueue.remove();
            } else {
                System.out.println("Order size is: " + orderQueue.size());
                System.out.println("Order top is: " + orderQueue.peek().timeStamp);
                olderThanOneMinute = false;
            }
        }
    }

}
