package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierMethod extends BaseMethod {

    // Создание курьера
    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    // Авторизация курьера
    @Step("Авторизовать курьера")
    public ValidatableResponse login(Courier courier) {
        return spec()
                .body(courier)
                .log().all()
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all();
    }

    // Получение ID курьера после авторизации
    @Step("Получить ID курьера")
    public int checkLogin(ValidatableResponse response) {
        return response
                .extract()
                .path("id");
    }

    // Удаление курьера
    @Step("Удалить курьера")
    public ValidatableResponse delete(int courierId) {
        return spec()
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then();
    }

    @Step("Авторизовать курьера без пароля")
    public ValidatableResponse loginWithoutPassword(String login) {

        CourierLoginData data = new CourierLoginData(login);

        return spec()
                .body(data)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

}