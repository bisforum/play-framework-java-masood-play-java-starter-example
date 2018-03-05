package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.Order;
import services.Statistics;
import services.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

/**
 * This controller demonstrates how to use dependency injection to
 * bind a component into a controller class. The class contains an
 * action that shows an incrementing getOrderStatistics to users. The {@link Order}
 * object is injected by the Guice dependency injection system.
 */
@Singleton
public class CountController extends Controller {

    private final Order orderService;

    @Inject
    public CountController(Order counter) {
        this.orderService = counter;
    }

    /**
     * An action that responds with the {@link Order}'s current
     * getOrderStatistics. The result is plain text. This action is mapped to
     * <code>GET</code> requests with a path of <code>/getOrderStatistics</code>
     * requests by an entry in the <code>routes</code> config file.
     */
    public Result getOrderStatistics() {
        Statistics stat = orderService.getStatistics(new Date());
        ObjectNode result = Json.newObject();
        result.put("total_sales_amount", stat.getTotalSalesAmount());
        result.put("average_amount_per_order:", stat.getAverageAmountPerOrder());
        return ok(result);
    }

    public Result addOrder() {
//        Transaction t = new Transaction(new Date(), 1);
        JsonNode json = request().body().asJson();
        int saleAmount = Integer.valueOf(json.get("sales_amount").asText());
        Transaction transaction = new Transaction(new Date(), saleAmount);
        orderService.addOrder(transaction);
        return status(202, "Accepted");
//        return ok(Integer.toString(orderService.getStatistics(new Date())));
    }

}
