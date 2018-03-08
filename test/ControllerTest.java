//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static play.mvc.Http.Status.OK;
//import static play.test.Helpers.*;
//
//
//import akka.actor.ActorSystem;
//import com.typesafe.config.Config;
//import controllers.AsyncController;
//import org.junit.Test;
//
//import play.mvc.Result;
//import play.twirl.api.Content;
//import scala.concurrent.ExecutionContextExecutor;
//import services.Order;
//
//import javax.inject.Inject;
//
//public class ControllerTest {
//
//    @Inject
//     ActorSystem actorSystem;
//
//    @Inject
//     ExecutionContextExecutor exec;
//
//    @Inject
//    Order orderService;
//
//    private Config config;
//
//    @Test
//    public void testIndex() {
//        Result result = new AsyncController(this.actorSystem,this.exec,this.orderService,this.config).addOrder();
//        assertEquals(OK, result.status());
//        assertEquals("text/html", result.contentType().get());
//        assertEquals("utf-8", result.charset().get());
//        assertTrue(contentAsString(result).contains("Welcome"));
//    }
//
//}