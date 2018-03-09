import com.fasterxml.jackson.databind.JsonNode;
import controllers.routes;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;

import static junit.framework.TestCase.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import play.mvc.Http.RequestBuilder;

public class TestController extends WithApplication {


    @Test
    public void testBadRoute() {
        RequestBuilder request = Helpers.fakeRequest()
                .header(CONTENT_TYPE,"application/json")
                .method(GET)
                .uri("/statistics");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }


    public static <T> Http.RequestBuilder fakeRequestWithJson(T input, String method, String url) {
        JsonNode jsonNode = Json.toJson(input);
        Http.RequestBuilder fakeRequest = Helpers.fakeRequest(method, url).bodyJson(jsonNode);
        System.out.println("Created fakeRequest=" + fakeRequest + ", body=" + fakeRequest.body().asJson());
        return fakeRequest;
    }
}