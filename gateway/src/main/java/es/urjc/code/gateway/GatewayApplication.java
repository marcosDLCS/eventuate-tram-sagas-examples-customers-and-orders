package es.urjc.code.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    private static final String ORDERS_BASE = "http://orderservice:8080/orders";
    private static final String CUSTOMERS_BASE = "http://customerservice:8080/customers";
    private static final String PRODUCTS_BASE = "http://productservice:8080/products";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(o -> o.path().uri(ORDERS_BASE))
                .route(o -> o.path("/**").uri(ORDERS_BASE))
                .route(c -> c.path().uri(CUSTOMERS_BASE))
                .route(c -> c.path("/**").uri(CUSTOMERS_BASE))
                .route(p -> p.path().uri(PRODUCTS_BASE))
                .route(p -> p.path("/**").uri(PRODUCTS_BASE))
                .build();
    }

}
