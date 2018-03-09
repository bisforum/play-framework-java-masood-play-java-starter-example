//import com.fasterxml.jackson.databind.JsonNode;
//import controllers.OrderController;
//import org.junit.Test;
//import play.libs.Json;
//import play.mvc.Result;
//import services.InMemoryOrderService;
//import play.test.Helpers;
//import javax.inject.Inject;
//import java.util.concurrent.CompletionStage;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.awaitility.Awaitility.await;
//
//
//public class AsyncControllerTest {
//
//    @Inject
//    InMemoryOrderService orderService;
//
//    // Unit test a controller with async return
//    @Test
//    public void testAddAndGetAsync() {
//
//
//        final OrderController controller = new OrderController(orderService);
//
//        JsonNode json = Json.parse("{\"sales_amount\":\"1\"}");
//
//        final CompletionStage<Result> future = Helpers.invokeWithContext(Helpers.fakeRequest().bodyJson(json), Helpers.contextComponents(), () -> controller.addOrder());
//
//
//        // Block until the result is completed
//        await().until(() -> {
//
//            assertThat(future.toCompletableFuture()).isCompletedWithValue(new Result(202));
//
//
////            assertThat(future.toCompletableFuture()).isCompletedWithValueMatching(result -> {
////                System.out.println(">>>>>" +contentAsString(result));
////                return contentAsString(result).equals("{\"total\":\"1\"}");
////            });
//
//
//        });
//
//    }
//
//
//}
