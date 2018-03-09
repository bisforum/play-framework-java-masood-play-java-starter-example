package controllers;

import akka.actor.ActorSystem;

import javax.inject.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.*;
import play.libs.Json;
import play.mvc.*;

import java.util.Date;
import java.util.concurrent.CompletionStage;

import scala.concurrent.ExecutionContextExecutor;
import scala.reflect.internal.AnnotationInfos;
import services.Order;
import services.Transaction;


@Singleton
public class AsyncController extends Controller {


    private final Order orderService;


    @Inject
    public AsyncController(Order order) {

        this.orderService = order;


    }

    //    }
    public CompletionStage<Result> addOrder() {

        JsonNode json = request().body().asJson();
        JsonNode amountValue = json.get("sales_amount");
        if (amountValue == null) {
//            todo: better error type should be returned both for field type and value type
            throw new IllegalArgumentException("\"sales_amount\" field is not found in the JSON body ");
        }
        int saleAmount = Integer.valueOf(amountValue.asText());
        Transaction transaction = new Transaction(new Date(), saleAmount);

        return orderService.addOrder(transaction).thenApply(result -> status(202, "Accepted"));
    }


    public CompletionStage<Result> getOrderStatistics() {

        return orderService.getStatistics(new Date()).thenApply(stat -> {
            ObjectNode result = Json.newObject();
            result.put("total_sales_amount", stat.getTotalSalesAmount());
            result.put("average_amount_per_order:", stat.getAverageAmountPerOrder());
            return ok(result);
        });

    }

}
