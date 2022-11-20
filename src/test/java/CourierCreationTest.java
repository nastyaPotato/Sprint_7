import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Courier;
import org.example.CourierClient;
import org.example.CourierCredentials;
import org.example.CourierGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CourierCreationTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefaultCourier();
    }

    @After
    public void cleanUp() {
        //получаем респонс логина созданного курьера, чтобы получит его айди и после теста удалить его по айди
        ValidatableResponse responseLogin = courierClient.login(CourierCredentials.from(courier));
        //достаем айди из респонса логина
        id = responseLogin.extract().path("id");
        if (id != null) {
            courierClient.delete(id);
        }
    }

    @Test
    @DisplayName("Positive test for creation of courier")
    public void courierCanBeCreated() {
        //получаем респонс создания курьера
        ValidatableResponse responseCreate = courierClient.create(courier);

        //получаем код респонса и боди
        boolean actualIsCourierCreated = responseCreate.extract().path("ok");
        int actualStatusCode = responseCreate.extract().statusCode();

        //задаем ожидаемый статус и боди
        int expectedStatusCode = 201;
        boolean expectedBoolean = true;

        //проверяем соотвествие
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedBoolean, actualIsCourierCreated);
    }

    @Test
    @DisplayName("Negative test for identical courier creation")
    public void identicalCourierCanNotBeCreated() {
        //создали курьера первый раз
        courierClient.create(courier);
        //создали такого же курьера второй раз и записали респонс
        ValidatableResponse responseCreate = courierClient.create(courier);

        //получаем код респонса и боди
        int actualStatusCode = responseCreate.extract().statusCode();
        String actualResponseErrorMessage = responseCreate.extract().path("message");

        //задаем ожидаемый статус и боди
        int expectedStatusCode = 409;
        String expectedResponseErrorMessage = "Этот логин уже используется";

        //проверяем соотвествие
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);
    }

    @Test
    @DisplayName("Negative test for creation of courier without password")
    public void courierWithoutPasswordCannotBeCreated() {
        //задали курьера без пароля
        courier.setPassword("");
        //отправили реквест на создание курьера без пароля и записали респонс
        ValidatableResponse responseCreate = courierClient.create(courier);

        //получаем код респонса и боди
        int actualStatusCode = responseCreate.extract().statusCode();
        String actualResponseErrorMessage = responseCreate.extract().path("message");

        //задаем ожидаемый статус и боди
        int expectedStatusCode = 400;
        String expectedResponseErrorMessage = "Недостаточно данных для создания учетной записи";

        //проверяем соотвествие
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);

    }

    @Test
    @DisplayName("Negative test for creation of courier without login")
    public void courierWithoutLoginCannotBeCreated() {
        //создали курьера без пароля
        courier.setLogin("");
        //отправили реквест на создание курьера без пароля и записали респонс
        ValidatableResponse responseCreate = courierClient.create(courier);

        //получаем код респонса и боди
        int actualStatusCode = responseCreate.extract().statusCode();
        String actualResponseErrorMessage = responseCreate.extract().path("message");

        //задаем ожидаемый статус и боди
        int expectedStatusCode = 400;
        String expectedResponseErrorMessage = "Недостаточно данных для создания учетной записи";

        //проверяем соотвествие
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);

    }
}
