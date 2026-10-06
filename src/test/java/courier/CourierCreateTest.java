package courier;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.is;

public class CourierCreateTest {

    private CourierMethod client = new CourierMethod();
    private Courier courier;

    @After
    public void deleteCourier() {

        if (courier == null) {
            return;
        }

        ValidatableResponse response = client.login(courier);

        int courierId = client.checkLogin(response);

        if (courierId > 0) {
            client.delete(courierId);
        }
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка успешного создания нового курьера")
    public void createCourierTest() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        ValidatableResponse response = client.create(courier);

        response
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание дубликата курьера")
    @Description("Проверка ошибки при попытке создать курьера с уже существующим логином")
    public void doubleCreatedTest() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        // Первый раз создаём курьера
        client.create(courier)
                .assertThat()
                .statusCode(SC_CREATED);

        // Второй раз создаём того же курьера
        client.create(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверка ошибки при создании курьера без логина")
    public void createCourierWithoutLoginTest() {

        Courier courier = new Courier();
        courier.setPassword("123456");

        client.create(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка ошибки при создании курьера без пароля")
    public void createCourierWithoutPasswordTest() {

        Courier courier = new Courier();
        courier.setLogin("courier_" + System.currentTimeMillis());

        client.create(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

}