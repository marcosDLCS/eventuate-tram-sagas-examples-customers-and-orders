package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi;

public class CreateOrderResponse {

    private final long orderId;

    public CreateOrderResponse(final long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }

}
