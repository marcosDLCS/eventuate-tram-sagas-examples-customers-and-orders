package io.eventuate.examples.tram.sagas.ordersandcustomers.endtoendtests;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.RejectionReason;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.CreateOrderRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.CreateOrderResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.GetOrderResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductResponse;
import io.eventuate.util.test.async.Eventually;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CustomersAndOrdersE2ETestConfiguration.class, webEnvironment =
        SpringBootTest.WebEnvironment.NONE)
public class CustomersAndOrdersE2ETest {

    private static final String ORDERS = "orders";
    private static final String CUSTOMERS = "customers";
    private static final String PRODUCTS = "products";

    private static final String MY_PRODUCT = "MyProduct";
    private static final String MY_NAME = "Fred";
    private static final String MY_MONEY = "15.00";

    @Value("#{systemEnvironment['DOCKER_HOST_IP']}")
    private String hostName;

    private String baseUrl(final String path, final String port) {
        return "http://" + hostName + ":" + port + "/" + path;
    }

    private String baseUrlOrders() {
        return baseUrl(ORDERS, "8081");
    }

    private String baseUrlCustomers() {
        return baseUrl(CUSTOMERS, "8082");
    }

    private String baseUrlProducts() {
        return baseUrl(PRODUCTS, "8083");
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void shouldApprove() {

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrlProducts(),
                new CreateProductRequest(MY_PRODUCT, 50), CreateProductResponse.class);

        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrlCustomers(),
                new CreateCustomerRequest(MY_NAME, new Money(MY_MONEY)), CreateCustomerResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrlOrders(),
                new CreateOrderRequest(new Money("12.34"),
                        createCustomerResponse != null ? createCustomerResponse.getCustomerId() : null,
                        createProductResponse != null ? createProductResponse.getProductId() : null,
                        10), CreateOrderResponse.class);

        assertOrderState(createOrderResponse != null ? createOrderResponse.getOrderId() : 0,
                OrderState.APPROVED, null);
    }

    @Test
    public void shouldRejectBecauseOfInsufficientCredit() {

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrlProducts(),
                new CreateProductRequest(MY_PRODUCT, 50), CreateProductResponse.class);

        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrlCustomers(),
                new CreateCustomerRequest(MY_NAME, new Money(MY_MONEY)), CreateCustomerResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrlOrders(),
                new CreateOrderRequest(new Money("123.40"),
                        createCustomerResponse != null ? createCustomerResponse.getCustomerId() : null,
                        createProductResponse != null ? createProductResponse.getProductId() : null,
                        10), CreateOrderResponse.class);

        assertOrderState(createOrderResponse != null ? createOrderResponse.getOrderId() : 0,
                OrderState.REJECTED, RejectionReason.INSUFFICIENT_CREDIT);
    }

    @Test
    public void shouldRejectBecauseOfUnknownCustomer() {

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrlProducts(),
                new CreateProductRequest(MY_PRODUCT, 50), CreateProductResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrlOrders(),
                new CreateOrderRequest(new Money(MY_MONEY),
                        Long.MAX_VALUE,
                        createProductResponse != null ? createProductResponse.getProductId() : null,
                        10), CreateOrderResponse.class);

        assertOrderState(createOrderResponse != null ? createOrderResponse.getOrderId() : 0,
                OrderState.REJECTED, RejectionReason.UNKNOWN_CUSTOMER);
    }

    @Test
    public void shouldRejectBecauseOfUnknownProduct() {

        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrlCustomers(),
                new CreateCustomerRequest(MY_NAME, new Money(MY_MONEY)), CreateCustomerResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrlOrders(),
                new CreateOrderRequest(new Money(MY_MONEY),
                        createCustomerResponse != null ? createCustomerResponse.getCustomerId() : null,
                        Long.MAX_VALUE,
                        10), CreateOrderResponse.class);

        assertOrderState(createOrderResponse != null ? createOrderResponse.getOrderId() : 0,
                OrderState.REJECTED, RejectionReason.UNKNOWN_PRODUCT);
    }

    @Test
    public void shouldRejectBecauseOfInsufficientProductStock() {

        CreateProductResponse createProductResponse = restTemplate.postForObject(baseUrlCustomers(),
                new CreateProductRequest(MY_PRODUCT, 50), CreateProductResponse.class);

        CreateCustomerResponse createCustomerResponse = restTemplate.postForObject(baseUrlCustomers(),
                new CreateCustomerRequest(MY_NAME, new Money(MY_MONEY)), CreateCustomerResponse.class);

        CreateOrderResponse createOrderResponse = restTemplate.postForObject(baseUrlOrders(),
                new CreateOrderRequest(new Money("123.40"),
                        createCustomerResponse != null ? createCustomerResponse.getCustomerId() : null,
                        createProductResponse != null ? createProductResponse.getProductId() : null,
                        500), CreateOrderResponse.class);

        assertOrderState(createOrderResponse != null ? createOrderResponse.getOrderId() : 0,
                OrderState.REJECTED, RejectionReason.INSUFFICIENT_STOCK);
    }

    private void assertOrderState(Long id, OrderState expectedState, RejectionReason expectedRejectionReason) {

        Eventually.eventually(() -> {
            ResponseEntity<GetOrderResponse> getOrderResponseEntity = restTemplate
                    .getForEntity(baseUrlOrders() + "/" + id, GetOrderResponse.class);
            assertEquals(HttpStatus.OK, getOrderResponseEntity.getStatusCode());
            GetOrderResponse order = getOrderResponseEntity.getBody();
            assertEquals(expectedState, order != null ? order.getOrderState() : null);
            assertEquals(expectedRejectionReason, order != null ? order.getRejectionReason() : null);
        });

    }

}
