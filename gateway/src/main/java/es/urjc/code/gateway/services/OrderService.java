package es.urjc.code.gateway.services;

import es.urjc.code.gateway.model.OrderDetails;
import io.eventuate.examples.tram.sagas.ordersandcustomers.orders.webapi.GetOrderResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.GetProductResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final WebClient orderClient;
    private final WebClient productClient;

    public OrderService(@Qualifier("ordersWebClient") final WebClient orderClient,
                        @Qualifier("productsWebClient") final WebClient productClient) {

        this.orderClient = orderClient;
        this.productClient = productClient;
    }

    public Mono<OrderDetails> getOrderDetails(final String orderId) {

        Mono<GetOrderResponse> order = orderClient
                .get().uri("/orders/" + orderId).retrieve().bodyToMono(GetOrderResponse.class).onErrorStop();

        final GetOrderResponse oResponse = order.block();

        Mono<GetProductResponse> product = productClient
                .get().uri("/clients/" + oResponse.getDetails().getProductId().toString())
                .retrieve().bodyToMono(GetProductResponse.class).onErrorStop();

        final GetProductResponse pResponse = product.block();

        return Mono.just(new OrderDetails(oResponse.getOrderId(), oResponse.getOrderState().name(),
                pResponse.getProductId(), pResponse.getProductName(), pResponse.getAvailableStock()));
    }
}
