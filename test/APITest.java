import org.junit.Test;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static junit.framework.TestCase.assertEquals;
import static play.mvc.Http.Status.*;

public class APITest extends WithServer {

    @Test
    public void GetStatistics_ValidCall_returnValidResponse() throws Exception {
        String url = "http://localhost:" + this.testServer.port() + "/statistics";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).setContentType("application/x-www-form-urlencoded").get();
            WSResponse response = stage.toCompletableFuture().get();
            assertEquals(200, response.getStatus());
            assertEquals(TestUtil.statisticsJson.toString(), response.getBody());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PostOrder_ValidCall_ReturnValidResponse() throws Exception {
        String url = "http://localhost:" + this.testServer.port() + "/sales";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).setContentType("application/x-www-form-urlencoded")
                    .post(TestUtil.orderPostTemplate("1"));
            WSResponse response = stage.toCompletableFuture().get();
            assertEquals(202, response.getStatus());
            assertEquals(ACCEPTED, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    todo: Test should be updated when ErrorHandler is improved
    @Test
    public void PostOrder_InvalidAmount_ReturnError() throws Exception {
        String url = "http://localhost:" + this.testServer.port() + "/sales";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).setContentType("application/x-www-form-urlencoded")
                    .post(TestUtil.orderPostTemplate("non_int"));
            WSResponse response = stage.toCompletableFuture().get();
            assertEquals(500, response.getStatus());
            assertEquals("A server error occurred: For input string: \"non_int\"", response.getBody());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //    todo: Test should be updated when ErrorHandler is improved
    @Test
    public void PostOrder_InvalidRequestBody_ReturnError() throws Exception {
        String url = "http://localhost:" + this.testServer.port() + "/sales";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).setContentType("application/x-www-form-urlencoded")
                    .post("{\"SOME_RANDOM\":\"VALUE\"}");
            WSResponse response = stage.toCompletableFuture().get();
            assertEquals(500, response.getStatus());
            assertEquals("A server error occurred: \"sales_amount\" field is not found in the JSON body ",
                    response.getBody());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}