package es.urjc.code.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    @Bean("ordersWebClient")
    public WebClient ordersWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://orderservice:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean("productsWebClient")
    public WebClient productsWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://productservice:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

}
