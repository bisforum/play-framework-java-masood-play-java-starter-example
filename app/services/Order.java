package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.ImplementedBy;
import com.google.inject.util.Modules;
import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * This interface demonstrates how to create a component that is injected
 * into a controller. The interface represents a counter that returns a
 * incremented number each time it is called.
 * <p>
 * The {@link Modules} class binds this interface to the
 * {@link InMemoryOrderService} implementation.
 */
public interface Order {

    final int queueCapacity = Integer.valueOf(Configuration.root().getString("queue.capacity"));

    // Note: Linked queues typically have higher throughput than array-based queues but less predictable performance in most concurrent applications.
    //       Also LinkedBlockingQueue uses two locks for this purpose. One for taking items from queue and other for putting items.
    @Singleton
    BlockingQueue<Transaction> orderQueue = new LinkedBlockingQueue<>(queueCapacity);


    CompletionStage<Void> addOrder(Transaction date);

    CompletionStage<Statistics> getStatistics(Date requestTime);

    CompletionStage<Statistics> computeSum();


}
