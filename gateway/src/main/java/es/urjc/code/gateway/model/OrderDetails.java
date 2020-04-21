package es.urjc.code.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetails {

    private Long orderId;

    private String orderState;

    private Long productId;

    private String productName;

    private Integer productAvailableStock;

}
