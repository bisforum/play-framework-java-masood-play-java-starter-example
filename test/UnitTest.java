import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import controllers.AsyncController;
import controllers.CountController;
import org.junit.Test;
import play.mvc.Result;
import scala.concurrent.ExecutionContextExecutor;
import services.InMemoryOrderService;
import services.Order;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static play.test.Helpers.contentAsString;

/**
 * Unit testing does not require Play application start up.
 * <p>
 * https://www.playframework.com/documentation/latest/JavaTest
 */
public class UnitTest {
    @Inject
    Config config;

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    // Unit test a controller
//    @Test
//    public void testCount() {
//        final CountController controller = new CountController(() -> 49);
//        Result result = controller.getOrderStatistics();
//        assertThat(contentAsString(result)).isEqualTo("49");
//    }

    // Unit test a controller with async return
    @Test
    public void testAsync() {
        final ActorSystem actorSystem = ActorSystem.create("test");
        final Order order = new InMemoryOrderService();
        try {
            final ExecutionContextExecutor ec = actorSystem.dispatcher();
            final AsyncController controller = new AsyncController(actorSystem, ec, order, config);
            final CompletionStage<Result> future = controller.getOrderStatistics();

            // Block until the result is completed
            await().until(() -> {
                assertThat(future.toCompletableFuture()).isCompletedWithValueMatching(result -> {
                    return contentAsString(result).equals("Hi!");
                });
            });
        } finally {
            actorSystem.terminate();
        }
    }

}
