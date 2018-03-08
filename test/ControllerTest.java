import akka.actor.ActorSystem;
import controllers.AsyncController;
import org.junit.Test;
import play.mvc.Result;
import scala.concurrent.ExecutionContextExecutor;
import services.InMemoryOrderService;
import services.Order;

import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static play.test.Helpers.contentAsString;

public class UnitTest {

    Order orderService = new InMemoryOrderService();

    // Unit test a controller with async return
    @Test
    public void testAsync() {
        final ActorSystem actorSystem = ActorSystem.create("test");
        try {
            final ExecutionContextExecutor ec = actorSystem.dispatcher();
            final AsyncController controller = new AsyncController(actorSystem, ec, orderService);
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