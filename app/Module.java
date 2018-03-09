import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import services.InMemoryOrderService;
import services.OrderService;
import services.Transaction;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Module extends AbstractModule {

    @Override
    public void configure() {

//        todo: Read the initialCapacity from configuration
        bind(new TypeLiteral<BlockingQueue<Transaction>>() {
        }).toInstance(new PriorityBlockingQueue<>(300000, Transaction::compareTo));
        bind(OrderService.class).to(InMemoryOrderService.class);


    }

}
