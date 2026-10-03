package courier;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethod {

    private static final String BASE_URL =
            "https://qa-scooter.praktikum-services.ru";

    // Создание заказа
    public ValidatableResponse create(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    // получение списка заказов
    public ValidatableResponse getOrders() {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/api/v1/orders")
                .then();
    }

    public ValidatableResponse getOrderByTrack(int track) {
        return given()
                .baseUri(BASE_URL)
                .queryParam("t", track)
                .when()
                .get("/api/v1/orders/track")
                .then();
    }

}