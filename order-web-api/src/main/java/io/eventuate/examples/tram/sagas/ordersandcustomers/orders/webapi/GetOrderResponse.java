package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi;

import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain.RejectionReason;

public final class GetOrderResponse {

    private final Long orderId;
    private final OrderState orderState;
    private final RejectionReason rejectionReason;

    public GetOrderResponse(final Long orderId, final OrderState orderState, final RejectionReason rejectionReason) {

        this.orderId = orderId;
        this.orderState = orderState;
        this.rejectionReason = rejectionReason;
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

}
