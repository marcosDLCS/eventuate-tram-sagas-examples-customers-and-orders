package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi;

import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.common.OrderDetails;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.RejectionReason;

public final class GetOrderResponse {

    private Long orderId;
    private OrderState orderState;
    private RejectionReason rejectionReason;
    private OrderDetails details;

    public GetOrderResponse() {
        // GetOrderResponse
    }

    public GetOrderResponse(final Long orderId, final OrderState orderState,
                            final RejectionReason rejectionReason, final OrderDetails details) {

        this.orderId = orderId;
        this.orderState = orderState;
        this.rejectionReason = rejectionReason;
        this.details = details;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public OrderDetails getDetails() {
        return details;
    }
}
