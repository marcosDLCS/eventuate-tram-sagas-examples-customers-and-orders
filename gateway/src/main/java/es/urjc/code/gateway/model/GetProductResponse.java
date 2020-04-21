package es.urjc.code.gateway.model;

public class GetProductResponse {

    private Long productId;

    private String productName;

    private int productStock;

    private int availableStock;

    public GetProductResponse(final Long productId, final String productName,
                              final int productStock, final int availableStock) {

        this.productId = productId;
        this.productName = productName;
        this.productStock = productStock;
        this.availableStock = availableStock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}
