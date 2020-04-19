package io.eventuate.examples.tram.sagas.ordersandcustomers.endtoendtests;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Configuration
public class CustomersAndOrdersE2ETestConfiguration {

    @Bean
    public RestTemplate restTemplate(HttpMessageConverters converters) {
        RestTemplate restTemplate = new RestTemplate();
        List<? extends HttpMessageConverter<?>> httpMessageConverters =
                Collections.singletonList(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters((List<HttpMessageConverter<?>>) httpMessageConverters);
        return restTemplate;
    }

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> additional = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(additional);
    }
}
