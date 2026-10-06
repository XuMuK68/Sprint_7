package courier;

import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class OrderListTest {

    private OrderMethod client = new OrderMethod();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов")
    public void getOrdersTest() {

        client.getOrders()
                .assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}