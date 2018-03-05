package services;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This interface demonstrates how to create a component that is injected
 * into a controller. The interface represents a counter that returns a
 * incremented number each time it is called.
 * <p>
 * The {@link Modules} class binds this interface to the
 * {@link InMemoryOrderService} implementation.
 */
public interface Order {


    Queue<Transaction> orderQueue = new LinkedList<>();
    void addOrder(Transaction date);
    Statistics getStatistics(Date requestTime);
}
