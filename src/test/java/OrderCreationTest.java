import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.Order;
import org.example.OrderClient;
import org.example.OrderGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private OrderClient orderClient;
    private Order order;
    private final int expectedStatusCode;
    private Integer track;

    public OrderCreationTest(Order order, int expectedStatusCode) {
        this.order = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] getInputs() {
        return new Object[][]{
                {OrderGenerator.getOrderWithoutColours(), 201},
                {OrderGenerator.getOrderWithTwoColours(), 201},
                {OrderGenerator.getOrderWithBlackColour(), 201},
                {OrderGenerator.getOrderWithGreyColour(), 201},
        };

    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = new Order();
    }

    @After
    public void cleanUp() {
        //отменяем заказ
        orderClient.cancel(track);
    }

    @Test
    @DisplayName("Positive test for order creation with different colours")
    public void orderCanBeCreatedWithDifferentColours() {
        //создаем заказ
        ValidatableResponse creationResponse = orderClient.create(order);

        //получаем код респонса и боди
        int actualStatusCode = creationResponse.extract().statusCode();
        track = creationResponse.extract().path("track");

        //проверяем, что трек не пустой и правильный статус код
        assertNotNull(track);
        assertEquals(expectedStatusCode, actualStatusCode);

    }
}
