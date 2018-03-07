package controllers;

import akka.actor.ActorSystem;

import javax.inject.*;

import akka.actor.Scheduler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Injector;
import com.typesafe.config.Config;
import play.*;
import play.libs.Json;
import play.mvc.*;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import scala.concurrent.ExecutionContextExecutor;
import services.InMemoryOrderService;
import services.Order;
import services.Statistics;
import services.Transaction;
import com.typesafe.config.Config;

/**
 * This controller contains an action that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 */
@Singleton
public class AsyncController extends Controller {

    private final ActorSystem actorSystem;
    private final ExecutionContextExecutor exec;
    private final Order orderService;
    private Config config;

    /**
     * @param actorSystem We need the {@link ActorSystem}'s
     *                    {@link Scheduler} to run code after a delay.
     * @param exec        We need a Java {@link Executor} to apply the result
     *                    of the {@link CompletableFuture} and a Scala
     *                    {@link ExecutionContext} so we can use the Akka {@link Scheduler}.
     *                    An {@link ExecutionContextExecutor} implements both interfaces.
     */
    @Inject
    public AsyncController(ActorSystem actorSystem, ExecutionContextExecutor exec, Order order, Config config) {
        this.actorSystem = actorSystem;
        this.exec = exec;
        this.orderService = order;
//        Order orderService = Inject.instanceOf(order);
        this.config = config;


    }


    /**
     * An action that returns a plain text message after a delay
     * of 1 second.
     * <p>
     * The configuration in the <code>routes</code> file means that this method
     * will be called when the application receives a <code>GET</code> request with
     * a path of <code>/message</code>.
     */
//    public CompletionStage<Result> message() {
//        return getFutureMessage(1, TimeUnit.SECONDS).thenApplyAsync(Results::ok, exec);
//    }
    public CompletionStage<Result> addOrder() {

        JsonNode json = request().body().asJson();
        int saleAmount = Integer.valueOf(json.get("sales_amount").asText());
        Transaction transaction = new Transaction(new Date(), saleAmount);

        return orderService.addOrder(transaction).thenApply(result -> status(202, "Accepted"));
    }


    public CompletionStage<Result> getOrderStatistics() {
    final int timeWindow = (int) config.getDuration("statistics.time.window", TimeUnit.SECONDS);
    System.out.println("TimeWinodw is : ----"+timeWindow);

        return orderService.getStatistics(new Date()).thenApply(stat -> {
            ObjectNode result = Json.newObject();
            result.put("total_sales_amount", stat.getTotalSalesAmount());
            result.put("average_amount_per_order:", stat.getAverageAmountPerOrder());
            return ok(result);
        });

    }

}
