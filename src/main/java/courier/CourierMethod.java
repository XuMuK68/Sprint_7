package courier;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierMethod {

    private static final String BASE_URL =
            "https://qa-scooter.praktikum-services.ru";

    // Создание курьера
    public ValidatableResponse create(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    // Авторизация курьера
    public ValidatableResponse login(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(courier)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all();
    }

    // Получение ID курьера после авторизации
    public int checkLogin(ValidatableResponse response) {
        return response
                .extract()
                .path("id");
    }

    // Удаление курьера
    public ValidatableResponse delete(int courierId) {
        return given()
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then();
    }
}
