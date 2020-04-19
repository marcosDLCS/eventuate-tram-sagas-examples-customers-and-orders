package io.eventuate.examples.tram.sagas.ordersandcustomers.integrationtests;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.Customer;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.service.CustomerService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common.OrderDetails;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.Order;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.service.OrderService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.Product;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public abstract class AbstractOrdersAndCustomersIntegrationTest {

    private static final String MY_PRODUCT = "MyProduct";
    private static final String MY_NAME = "Fred";
    private static final String MY_MONEY = "15.00";

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void shouldApproveOrder() throws InterruptedException {

        Money creditLimit = new Money(MY_MONEY);
        Product product = productService.createProduct(MY_PRODUCT, 2);
        Customer customer = customerService.createCustomer(MY_NAME, creditLimit);
        Order order = orderService.createOrder(new OrderDetails(customer.getId(), new Money("12.34"),
                product.getId(), 1));

        assertOrderState(order.getId(), OrderState.APPROVED);
    }

    @Test
    public void shouldRejectOrder() throws InterruptedException {

        Money creditLimit = new Money(MY_MONEY);
        Product product = productService.createProduct(MY_PRODUCT, 2);
        Customer customer = customerService.createCustomer(MY_NAME, creditLimit);
        Order order = orderService.createOrder(new OrderDetails(customer.getId(), new Money("123.40"),
                product.getId(), 1));

        assertOrderState(order.getId(), OrderState.REJECTED);
    }

    private void assertOrderState(Long id, OrderState expectedState) throws InterruptedException {
        Order order = null;
        for (int i = 0; i < 30; i++) {
            order = Objects.requireNonNull(transactionTemplate
                    .execute(s -> orderRepository.findById(id)))
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Order with id %s is not found",
                            id)));
            if (order.getState() == expectedState)
                break;
            TimeUnit.MILLISECONDS.sleep(400);
        }

        assertEquals(expectedState, order.getState());
    }
}
