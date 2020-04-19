package io.eventuate.examples.tram.sagas.ordersandcustomers.products.api.commands;

import io.eventuate.tram.commands.common.Command;

public class ReserveStockCommand implements Command {

    private Long productId;
    private Integer productStock;

    public ReserveStockCommand() {
        // ReserveStockCommand
    }

    public ReserveStockCommand(Long productId, Integer productStock) {
        this.productId = productId;
        this.productStock = productStock;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getProductStock() {
        return productStock;
    }
}
