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
import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer id;
    private ValidatableResponse responseLogin;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getDefaultCourier();
        courierClient.create(courier);
    }

    @After
    public void cleanUp() {
        //достаем айди из респонса логина
        id = responseLogin.extract().path("id");
        if (id != null) {
            courierClient.delete(id);
        }
    }


    @Test
    @DisplayName("Positive test for courier login")
    public void courierCanLogin() {
        //получаем респонс логина созданного курьера
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        //получаем код респонса и боди
        id = responseLogin.extract().path("id");
        int actualStatusCode = responseLogin.extract().statusCode();

        //задаем ожидаемый статус
        int expectedStatusCode = 200;

        //проверяем статус и что вернулось айди
        assertEquals(expectedStatusCode, actualStatusCode);
        assertNotNull(id);
    }

    @Test
    @DisplayName("Negative test for login of courier without password")
    public void courierCanNotLoginWithoutPassword() {
        //задали курьера без пароля
        courier.setPassword("");
        //получаем респонс логина созданного курьера
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        //получаем код респонса и боди
        int actualStatusCode = responseLogin.extract().statusCode();
        String actualResponseErrorMessage = responseLogin.extract().path("message");

        //задаем ожидаемый статус и ошибку
        int expectedStatusCode = 400;
        String expectedResponseErrorMessage = "Недостаточно данных для входа";

        //проверяем статус и что вернулась правильная ошибка
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);
    }

    @Test
    @DisplayName("Negative test for login of courier without login")
    public void courierCanNotLoginWithoutLogin() {
        //задали курьера без логина
        courier.setLogin("");
        //получаем респонс логина созданного курьера
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        //получаем код респонса и боди
        int actualStatusCode = responseLogin.extract().statusCode();
        String actualResponseErrorMessage = responseLogin.extract().path("message");

        //задаем ожидаемый статус и ошибку
        int expectedStatusCode = 400;
        String expectedResponseErrorMessage = "Недостаточно данных для входа";

        //проверяем статус и что вернулась правильная ошибка
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);
    }

    @Test
    @DisplayName("Negative test for login of courier with non existing login")
    public void courierCanNotLoginWithNonExistingLogin() {
        //задали несуществующий логин курьеру, созданному в бефор
        courier.setLogin("3232233");
        //получаем респонс логина созданного курьера
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        //получаем код респонса и боди
        int actualStatusCode = responseLogin.extract().statusCode();
        String actualResponseErrorMessage = responseLogin.extract().path("message");

        //задаем ожидаемый статус и ошибку
        int expectedStatusCode = 404;
        String expectedResponseErrorMessage = "Учетная запись не найдена";

        //проверяем статус и что вернулась правильная ошибка
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);
    }

    @Test
    @DisplayName("Negative test for login of courier with incorrect password")
    public void courierCanNotLoginWithIncorrectPassword() {
        //задали неправильный пароль курьеру, созданному в бефор
        courier.setPassword("333");
        //получаем респонс логина созданного курьера
        responseLogin = courierClient.login(CourierCredentials.from(courier));

        //получаем код респонса и боди
        int actualStatusCode = responseLogin.extract().statusCode();
        String actualResponseErrorMessage = responseLogin.extract().path("message");

        //задаем ожидаемый статус и ошибку
        int expectedStatusCode = 404;
        String expectedResponseErrorMessage = "Учетная запись не найдена";

        //проверяем статус и что вернулась правильная ошибка
        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedResponseErrorMessage, actualResponseErrorMessage);
    }
}
