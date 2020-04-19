package io.eventuate.examples.tram.sagas.ordersandcustomers.products;

import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductCommandHandler;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductService;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcherFactory;
import io.eventuate.tram.sagas.spring.participant.SagaParticipantConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import(SagaParticipantConfiguration.class)
@EnableJpaRepositories
@EnableAutoConfiguration
public class ProductConfiguration {

    @Bean
    public ProductService productService(final ProductRepository productRepository) {
        return new ProductService(productRepository);
    }

    @Bean
    public ProductCommandHandler productCommandHandler(final ProductService productService) {
        return new ProductCommandHandler(productService);
    }

    @Bean
    public CommandDispatcher productCommandDispatcher(final ProductCommandHandler target,
                                                      final SagaCommandDispatcherFactory sagaCommandDispatcherFactory) {

        return sagaCommandDispatcherFactory.make("productCommandDispatcher",
                target.commandHandlerDefinitions());
    }

}
