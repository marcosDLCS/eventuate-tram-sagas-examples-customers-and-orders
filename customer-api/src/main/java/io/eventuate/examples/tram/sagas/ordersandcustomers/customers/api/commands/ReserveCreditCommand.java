package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.api.commands;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.tram.commands.common.Command;

public class ReserveCreditCommand implements Command {

    private Long orderId;
    private Money orderTotal;
    private long customerId;

    public ReserveCreditCommand() {
        // ReserveCreditCommand
    }

    public ReserveCreditCommand(Long customerId, Long orderId, Money orderTotal) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderTotal = orderTotal;
    }

    public Money getOrderTotal() {
        return orderTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public long getCustomerId() {
        return customerId;
    }
}
