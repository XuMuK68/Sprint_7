package courier;

import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    private OrderMethod client = new OrderMethod();

    @Test
    public void getOrdersTest() {

        client.getOrders()
                .assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}