package services;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletionStage;

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

    CompletionStage<Void> addOrder(Transaction date);

    CompletionStage<Statistics> getStatistics(Date requestTime);
}
