
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

import com.fasterxml.jackson.databind.ObjectMapper;

// #ws-imports
import org.slf4j.Logger;
import play.api.Configuration;
import play.core.j.JavaHandlerComponents;
import play.libs.concurrent.Futures;
import play.libs.ws.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.concurrent.*;
// #ws-imports

// #json-imports
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
// #json-imports

// #multipart-imports
import play.mvc.Http.MultipartFormData.*;
// #multipart-imports

import java.io.*;
import java.util.Optional;
import java.util.stream.*;

import org.w3c.dom.Document;
import play.mvc.Result;

import javax.inject.Inject;

import play.http.HttpEntity;
import play.mvc.Http.Status;

// #ws-client-imports
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import play.mvc.Results;

import static play.test.Helpers.NOT_FOUND;

// #test-withserver
public class ServerFunctionalTest extends WithServer {

    @Inject
    WSClient ws;


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
        String url = "http://localhost:" + this.testServer.port() + "/getstatistics";
        int result = 0;
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).get();
            WSResponse response = stage.toCompletableFuture().get();
            System.out.println("Status Code >>>>>>> "+ response.getStatus());
            result =   response.getStatus();
//            assertEquals(NOT_FOUND, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
return  result;

    }

    @After
    public void testCount() throws Exception {
        assertEquals("4 Threads running addOne in parallel should lead to 4", 404, getStat());
    }

}