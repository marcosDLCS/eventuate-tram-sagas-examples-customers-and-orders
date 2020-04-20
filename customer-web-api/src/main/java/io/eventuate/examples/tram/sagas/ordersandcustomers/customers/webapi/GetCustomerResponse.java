package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi;

public class GetCustomerResponse {

    private Long customerId;

    private String customerName;

    public GetCustomerResponse(Long customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
