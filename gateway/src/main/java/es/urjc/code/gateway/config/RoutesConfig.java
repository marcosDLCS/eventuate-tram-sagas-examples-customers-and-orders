package es.urjc.code.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    private static final String GET = "GET";
    private static final String POST = "POST";

    private static final String ORDERS_BASE = "http://orderservice:8080";
    private static final String CUSTOMERS_BASE = "http://customerservice:8080";
    private static final String PRODUCTS_BASE = "http://productservice:8080";

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(o -> o.path("/orders").and().method(POST).uri(ORDERS_BASE))
                .route(o -> o.path("/orders/**").and().method(GET).uri(ORDERS_BASE))
                .route(c -> c.path("/customers").and().method(POST).uri(CUSTOMERS_BASE))
                .route(c -> c.path("/customers/**").and().method(GET).uri(CUSTOMERS_BASE))
                .route(p -> p.path("/products").and().method(POST).uri(PRODUCTS_BASE))
                .route(p -> p.path("/products/**").and().method(GET).uri(PRODUCTS_BASE))
                .build();
    }

}
