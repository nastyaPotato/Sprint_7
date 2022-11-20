package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private static final String PATH = "/api/v1/orders";

    @Step("Order creation")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(PATH)
                .then();
    }

    @Step("Order cancellation")
    public ValidatableResponse cancel(Integer track) {
        return given()
                .spec(getSpec())
                .when()
                .put(PATH + "/cancel" + "?track=" + track)
                .then();
    }

    @Step("Getting Order List")
    public ValidatableResponse getListOfOrders() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH)
                .then();
    }
}
