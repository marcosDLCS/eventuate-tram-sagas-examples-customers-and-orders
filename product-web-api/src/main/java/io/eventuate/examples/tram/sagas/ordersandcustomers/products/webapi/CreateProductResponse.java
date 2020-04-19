package io.eventuate.examples.tram.sagas.ordersandcustomers.products.webapi;

public class CreateProductResponse {

    private Long productId;

    public CreateProductResponse() {
        // CreateProductResponse
    }

    public CreateProductResponse(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}
