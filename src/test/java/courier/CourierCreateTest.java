package courier;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

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
    public void createCourierTest() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        ValidatableResponse response = client.create(courier);

        response
                .assertThat()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void doubleCreatedTest() {

        courier = new Courier(
                "courier_" + System.currentTimeMillis(),
                "123456"
        );

        // Первый раз создаём курьера
        client.create(courier)
                .assertThat()
                .statusCode(201);

        // Второй раз создаём того же курьера
        client.create(courier)
                .assertThat()
                .statusCode(409)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void createCourierWithoutLoginTest() {

        Courier courier = new Courier();
        courier.setPassword("123456");

        client.create(courier)
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void createCourierWithoutPasswordTest() {

        Courier courier = new Courier();
        courier.setLogin("courier_" + System.currentTimeMillis());

        client.create(courier)
                .assertThat()
                .statusCode(400);
    }

}