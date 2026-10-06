package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.Matchers.is;

public class CourierLoginWithoutCourierTest {

    private CourierMethod client = new CourierMethod();

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Проверка ошибки при авторизации курьера с несуществующим логином")
    public void loginWithFakeLoginTest() {

        Courier courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        client.login(courier)
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка ошибки при авторизации без обязательного поля логина")
    public void loginWithoutLoginTest() {

        Courier courier = new Courier();
        courier.setPassword("123456");

        client.login(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка ошибки при авторизации без обязательного поля пароля")
    public void loginWithoutPasswordTest() {

        String login = "courier_" + System.currentTimeMillis();

        client.loginWithoutPassword(login)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }
}