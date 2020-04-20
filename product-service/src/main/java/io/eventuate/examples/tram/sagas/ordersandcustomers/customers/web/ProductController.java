package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.web;

import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.Product;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain.ProductRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.service.ProductService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.CreateProductResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi.GetProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService productService;

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(final ProductService productService, final ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @PostMapping(value = "/products")
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest createProductRequest) {

        Product product = productService.createProduct(createProductRequest.getName(),
                createProductRequest.getStock());
        return new CreateProductResponse(product.getId());
    }

    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<GetProductResponse> getProduct(@PathVariable Long productId) {

        return productRepository
                .findById(productId)
                .map(p -> new ResponseEntity<>(
                        new GetProductResponse(p.getId(), p.getName(), p.getStock(), p.availableStock()),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
