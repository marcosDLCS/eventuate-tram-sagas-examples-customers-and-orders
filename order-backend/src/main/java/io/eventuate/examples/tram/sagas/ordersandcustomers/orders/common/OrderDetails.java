package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class OrderDetails {

    private Long customerId;

    @Embedded
    private Money orderTotal;

    private Long productId;

    private Integer productAmount;

    public OrderDetails() {
        // OrderDetails
    }

    public OrderDetails(final Long customerId, final Money orderTotal,
                        final Long productId, final Integer productAmount) {

        this.customerId = customerId;
        this.orderTotal = orderTotal;
        this.productId = productId;
        this.productAmount = productAmount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getProductAmount() {
        return productAmount;
    }
}
