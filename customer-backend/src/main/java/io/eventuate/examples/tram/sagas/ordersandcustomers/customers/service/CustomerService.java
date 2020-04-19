package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.service;

import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.Money;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.Customer;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.CustomerNotFoundException;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.CustomerRepository;

public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, Money creditLimit) {

        Customer customer = new Customer(name, creditLimit);
        return customerRepository.save(customer);
    }

    public void reserveCredit(long customerId, long orderId, Money orderTotal) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);
        customer.reserveCredit(orderId, orderTotal);
    }
}
