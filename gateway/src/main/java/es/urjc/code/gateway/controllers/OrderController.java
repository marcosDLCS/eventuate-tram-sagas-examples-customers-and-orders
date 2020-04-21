package es.urjc.code.gateway.controllers;

import es.urjc.code.gateway.model.OrderDetails;
import es.urjc.code.gateway.services.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order_aggregates")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Mono<OrderDetails> getOrderDetails(@PathVariable String id) {
        return orderService.getOrderDetails(id);
    }

}
