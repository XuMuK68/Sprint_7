package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrderMethod extends BaseMethod {

    // Создание заказа
    @Step("Создать заказ")
    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    // Получение списка заказов
    @Step("Получить список заказов")
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get("/api/v1/orders")
                .then();
    }

    // Получение заказа по трек-номеру
    @Step("Получить заказ по трек-номеру")
    public ValidatableResponse getOrderByTrack(int track) {
        return spec()
                .queryParam("t", track)
                .when()
                .get("/api/v1/orders/track")
                .then();
    }
}