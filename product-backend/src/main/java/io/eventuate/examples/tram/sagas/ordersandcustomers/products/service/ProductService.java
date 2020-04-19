package io.eventuate.examples.tram.sagas.ordersandcustomers.products.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.Product;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductNotFoundException;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductRepository;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public Product createProduct(final String name, final int stock) {

        Product product = new Product(name, stock);
        return productRepository.save(product);
    }

    public void reserveStock(final long productId, final int stock) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        product.reserveStock(productId, stock);
    }
}
