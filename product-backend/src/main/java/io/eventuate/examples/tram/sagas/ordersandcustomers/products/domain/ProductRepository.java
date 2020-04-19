package io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    // ProductRepository
}
