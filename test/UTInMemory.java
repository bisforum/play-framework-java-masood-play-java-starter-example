import static org.junit.Assert.*;

import org.junit.Test;
import services.InMemoryOrderService;

import javax.inject.Inject;
import java.util.Date;

public class UTInMemory {

    @Inject
    InMemoryOrderService inmemoryOrderService ;

    @Test
    public void testSum() {
        inmemoryOrderService.getStatistics(new Date());
    }

    @Test
    public void testString() {
        String str = "Hello world";
        assertFalse(str.isEmpty());
    }

}