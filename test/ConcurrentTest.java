//import static org.junit.Assert.assertEquals;
//
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
//import services.InMemoryOrderService;
//import services.Order;
//import services.Transaction;
//
//import javax.inject.Inject;
//import java.util.Date;
//
//@RunWith(ConcurrentTestRunner.class)
//
//public class ConcurrentTest {
//
////    private final Order orderService;
////
////
////    @Inject
////    public ConcurrentTest(Order order) {
////        this.orderService = order;
////    }
//
//    @Inject
//    Order orderService = new InMemoryOrderService();
//
//
//    @Test
//    public void addOne() {
//        orderService.addOrder(new Transaction(new Date(), 1));
//    }
//
//    @After
//    public void testCount() {
//        assertEquals("4 Threads running addOne in parallel should lead to 4", 4, orderService.getQueue());
//    }
//}
