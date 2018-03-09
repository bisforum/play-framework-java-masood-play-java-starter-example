import com.google.inject.AbstractModule;


import com.google.inject.TypeLiteral;
import controllers.AsyncController;
import play.Configuration;
import services.InMemoryOrderService;
import services.OrderService;
import services.Transaction;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        final int queueCapacity = Integer.valueOf(Configuration.root().getString("queue.capacity"));

        bind(new TypeLiteral<BlockingQueue<Transaction>>(){}).toInstance(new PriorityBlockingQueue<>(queueCapacity, Transaction::compareTo));
        bind(OrderService.class).to(InMemoryOrderService.class);
        bind(AsyncController.class);

    }

}
