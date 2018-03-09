import com.typesafe.config.Config;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import services.InMemoryOrderService;
import services.Statistics;
import services.Transaction;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.PriorityBlockingQueue;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class InMemoryOrderServiceUT {


    private PriorityBlockingQueue<Transaction> orderQueue = new PriorityBlockingQueue<>(100000, Transaction::compareTo);

    private DateTime currentTime = new DateTime();

    private int t1Amount = 1;
    private int t2Amount = 2;
    private int t3Amount = 3;
    private int t4Amount = 4;
    private Transaction t1 = new Transaction(currentTime.minusSeconds(59), t1Amount);
    private Transaction t2 = new Transaction(currentTime.minusSeconds(58), t2Amount);

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void Dequeue_AllOrdersAreOld_FlushQueue() throws ExecutionException, InterruptedException {

        Config config = mock(Config.class);
        makeExpiredQueue(currentTime);
        InMemoryOrderService service = new InMemoryOrderService(config, orderQueue);
        CompletableFuture<Void> futureResult = service.dequeueOldOrders(currentTime);
        futureResult.get();

        assertEquals((orderQueue.size()), 0);
    }

    @Test
    public void Dequeue_HalfOfOrdersAreOld_FlushHalfOfQueue() throws ExecutionException, InterruptedException {

        Config config = mock(Config.class);
        makeHalfExpiredQueue(currentTime);

        InMemoryOrderService service = new InMemoryOrderService(config, orderQueue);
        CompletableFuture<Void> futureResult = service.dequeueOldOrders(currentTime);
        futureResult.get();

        assertEquals((orderQueue.size()), 2);
        //      Assert if valid transactions are remained in the queue
        assertEquals((orderQueue.remove().compareTo(t1)), 0);
        assertEquals((orderQueue.remove().compareTo(t2)), 0);
    }

    @Test
    public void GetStatistics_HalfOfOrdersAreOld_GetHalfAmount() throws ExecutionException, InterruptedException {

        Config config = mock(Config.class);
        makeHalfExpiredQueue(currentTime);
        InMemoryOrderService service = new InMemoryOrderService(config, orderQueue);

        CompletionStage<Statistics> futureResult = service.getStatistics(currentTime);
        Statistics result = futureResult.toCompletableFuture().get();

        assertEquals(result.getTotalSalesAmount(), t3Amount + t4Amount);

    }


    private void makeExpiredQueue(DateTime currentTime) {

        orderQueue.add(new Transaction(currentTime.minusMinutes(2), t1Amount));
        orderQueue.add(new Transaction(currentTime.minusMinutes(3), t2Amount));
        orderQueue.add(new Transaction(currentTime.minusMinutes(2), t3Amount));
    }

    private void makeHalfExpiredQueue(DateTime currentTime) {

        makeFreshQueue();
        orderQueue.add(new Transaction(currentTime.minusMinutes(2), t3Amount));
        orderQueue.add(new Transaction(currentTime.minusMinutes(2), t4Amount));
    }

    private void makeFreshQueue() {

        orderQueue.add(t1);
        orderQueue.add(t2);

    }

}
