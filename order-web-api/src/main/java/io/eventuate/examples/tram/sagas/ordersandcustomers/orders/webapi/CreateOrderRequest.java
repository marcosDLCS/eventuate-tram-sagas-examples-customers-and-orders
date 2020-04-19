package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;

import javax.validation.constraints.NotNull;

public final class CreateOrderRequest {

    @NotNull(message = "OrderTotal must not be null")
    private Money orderTotal;
    @NotNull(message = "CustomerId must not be null")
    private Long customerId;
    @NotNull(message = "ProductId must not be null")
    private Long productId;
    @NotNull(message = "ProductAmount must not be null")
    private Integer productAmount;

    public CreateOrderRequest(final Money orderTotal, final Long customerId,
                              final Long productId, final Integer productAmount) {

        this.orderTotal = orderTotal;
        this.customerId = customerId;
        this.productId = productId;
        this.productAmount = productAmount;
    }

    public CreateOrderRequest() {
        // CreateOrderRequest
    }

    public Money getOrderTotal() {
        return orderTotal;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getProductAmount() {
        return productAmount;
    }
}
