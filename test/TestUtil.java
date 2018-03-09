import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

public class TestUtil {

    static JsonNode statisticsJson = Json.parse(
            "{\"total_sales_amount\":0,\"average_amount_per_order:\":0}"
    );

    static String orderPostTemplate(String amount) {
        return String.format("sales_amount=%s", amount);
    }


}
