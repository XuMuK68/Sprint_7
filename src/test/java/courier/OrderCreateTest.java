package courier;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private OrderMethod client = new OrderMethod();

    private String[] color;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {null}
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с разными вариантами цвета самоката")
    public void createOrderTest() {

        Order order = new Order(
                "Александр",
                "Гаврилов",
                "Москва, улица Сезам, 1",
                "Сокольники",
                "+78005553535",
                3,
                "2026-10-03",
                "Тестовый заказ",
                color
        );

        client.create(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}