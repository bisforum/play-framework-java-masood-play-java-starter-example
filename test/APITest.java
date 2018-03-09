import org.junit.Test;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static junit.framework.TestCase.assertEquals;
import static play.mvc.Http.Status.*;

public class APITest extends WithServer {

    @Test
    public void testInServer() throws Exception {
        int timeout = 5000;
        String url = "http://localhost:" + this.testServer.port() + "/statistics";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).setContentType("application/json").get();
            WSResponse response = stage.toCompletableFuture().get();
//            assertEquals(response.getBody() , "fdfdf");
            assertEquals(OK, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}