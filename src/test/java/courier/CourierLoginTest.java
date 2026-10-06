package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private CourierMethod client = new CourierMethod();
    private Courier courier;

    @Before
    public void createCourier() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        client.create(courier)
                .assertThat()
                .statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка успешной авторизации зарегистрированного курьера")
    public void loginCourierTest() {

        ValidatableResponse response = client.login(courier);

        response
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Проверка ошибки при авторизации курьера с неверным паролем")
    public void loginWithBadPasswordTest() {

        Courier wrongCourier = new Courier(
                courier.getLogin(),
                "badPassword"
        );

        client.login(wrongCourier)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

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
}