import com.google.inject.AbstractModule;

import java.time.Clock;

import controllers.AsyncController;
import services.ApplicationTimer;
import services.InMemoryOrderService;
import services.Order;

import javax.inject.Singleton;

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
        // Use the system clock as the default implementation of Clock
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        // Ask Guice to create an instance of ApplicationTimer when the
        // application starts.
        bind(ApplicationTimer.class).asEagerSingleton();
        // Set InMemoryOrderService as the implementation for Order.
        bind(Order.class).to(InMemoryOrderService.class);
        bind(AsyncController.class);
        bind(InMemoryOrderService.class);
    }

}
