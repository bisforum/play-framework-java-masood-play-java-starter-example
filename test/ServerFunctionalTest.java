
import java.io.IOException;
import java.util.concurrent.*;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.*;

import play.libs.Json;
import play.mvc.*;
import play.test.*;
import play.libs.F.*;
import play.libs.ws.*;
import play.libs.ws.WSClient;
import services.Statistics;
import services.Transaction;


import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static play.test.Helpers.NOT_FOUND;

// #test-withserver
public class ServerFunctionalTest extends WithServer {

    @Test
    public void testInServer() throws Exception {
        int timeout = 5000;
        String url = "http://localhost:" + this.testServer.port() + "/aa";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).get();
            WSResponse response = stage.toCompletableFuture().get();
//            assertEquals(NOT_FOUND, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getStat() throws Exception {
        String url = "http://localhost:" + this.testServer.port() + "/aa";
        int result = 0;
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).get();
            WSResponse response = stage.toCompletableFuture().get();
            JsonNode e = response.asJson();
            Statistics tt = Json.fromJson(e, Statistics.class);
            result = tt.getTotalSalesAmount();
//             result =  response.asJson().get("total_sales_amount").asInt();
//            assertEquals(NOT_FOUND, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    @After
    public void testCount() throws Exception {
        assertEquals("4 Threads running addOne in parallel should lead to 4", 4, getStat());
    }

}