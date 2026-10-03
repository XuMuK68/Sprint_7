package courier;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private CourierMethod client = new CourierMethod();
    private Courier courier;

    @Test
    public void loginCourierTest() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        client.create(courier)
                .assertThat()
                .statusCode(201);

        ValidatableResponse response = client.login(courier);

        response
                .assertThat()
                .statusCode(200)
                .body("id", notNullValue());
    }

        @Test //неверный пароль
    public void loginWithBadPasswordTest() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        client.create(courier)
                .assertThat()
                .statusCode(201);

        Courier wrongCourier = new Courier(
                courier.getLogin(),
                "badPassword"
        );

        client.login(wrongCourier)
                .assertThat()
                .statusCode(404);
    }

    @Test//неверный логин
    public void loginWithFakeLoginTest() {

        Courier courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        client.login(courier)
                .assertThat()
                .statusCode(404);
    }
// нет логина
    @Test
    public void loginWithoutLoginTest() {

        Courier courier = new Courier();
        courier.setPassword("123456");

        client.login(courier)
                .assertThat()
                .statusCode(400);
    }
        //нет пароля. код ошибки: 504. это баг???????
    @Test
    public void loginWithoutPasswordTest() {

        String login = "courier_" + System.currentTimeMillis();

        given()
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .header("Content-Type", "application/json")
                .body("{\"login\":\"" + login + "\"}")
                .when()
                .post("/api/v1/courier/login")
                .then()
                .statusCode(400);
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