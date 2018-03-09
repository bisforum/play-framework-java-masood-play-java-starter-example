package controllers;

import akka.actor.ActorSystem;

import javax.inject.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.*;
import play.libs.Json;
import play.mvc.*;
import play.Logger;

import java.util.concurrent.CompletionStage;

import services.OrderService;
import services.Transaction;


@Singleton
public class AsyncController extends Controller {


    private final OrderService orderService;


    @Inject
    public AsyncController(OrderService order) {

        this.orderService = order;


    }

    //    }
    public CompletionStage<Result> addOrder() {
        Logger.debug("Entered addOrder method; remoteAddress=" + request().remoteAddress());
        JsonNode json = request().body().asJson();
        JsonNode amountValue = json.get("sales_amount");
        if (amountValue == null) {
            Logger.debug("sales_amount field is not found in the request body: " + json.asText());

//            todo: Good client error type should be returned both for field type and value type
            throw new IllegalArgumentException("\"sales_amount\" field is not found in the JSON body ");
        }
        int saleAmount = Integer.valueOf(amountValue.asText());
        Transaction transaction = new Transaction(new DateTime(), saleAmount);

        return orderService.addOrder(transaction).thenApply(result -> status(202, "Accepted"));
    }


    public CompletionStage<Result> getOrderStatistics() {
        Logger.debug("Entered getOrderStatistics method; remoteAddress=" + request().remoteAddress());

        return orderService.getStatistics(new DateTime()).thenApply(stat -> {
            ObjectNode result = Json.newObject();
            result.put("total_sales_amount", stat.getTotalSalesAmount());
            result.put("average_amount_per_order:", stat.getAverageAmountPerOrder());
            return ok(result);
        });

    }

}
