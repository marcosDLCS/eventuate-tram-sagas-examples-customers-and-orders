package io.eventuate.examples.tram.sagas.ordersandcustomers.products.domain;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "Product")
@Access(AccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int stock;

    @ElementCollection
    private Map<Long, Integer> stockReservations;

    Integer availableStock() {
        return stock - stockReservations.values().stream().reduce(0, Integer::sum);
    }

    public Product() {
        // Product
    }

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void reserveStock(final Long productId, final int stock) {

        if (availableStock() >= stock) {
            stockReservations.put(productId, stock);
        } else
            throw new ProductNotAvailableStockException();
    }
}
