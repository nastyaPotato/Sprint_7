import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Positive test for getting list of orders")
    public void listOfOrdersIsReturned() {
        ValidatableResponse listOfOrdersResponse = orderClient.getListOfOrders();

        int expectedStatusCode = 200;

        int actualStatusCode = listOfOrdersResponse.extract().statusCode();
        ArrayList orders = listOfOrdersResponse.extract().path("orders");

        assertNotNull(orders);
        assertEquals(expectedStatusCode, actualStatusCode);
    }
}
