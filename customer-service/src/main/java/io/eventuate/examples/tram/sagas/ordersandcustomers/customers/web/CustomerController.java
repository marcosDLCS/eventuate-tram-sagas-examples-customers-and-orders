package io.eventuate.examples.tram.sagas.ordersandcustomers.customers.web;

import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.Customer;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.domain.CustomerRepository;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.service.CustomerService;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerRequest;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.CreateCustomerResponse;
import io.eventuate.examples.tram.sagas.ordersandcustomers.customers.webapi.GetCustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(final CustomerService customerService,
                              final CustomerRepository customerRepository) {

        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @PostMapping(value = "/customers")
    public CreateCustomerResponse createCustomer(@RequestBody final CreateCustomerRequest createCustomerRequest) {

        Customer customer = customerService.createCustomer(createCustomerRequest.getName(),
                createCustomerRequest.getCreditLimit());
        return new CreateCustomerResponse(customer.getId());
    }

    @GetMapping(value = "/customers/{customerId}")
    public ResponseEntity<GetCustomerResponse> getProduct(@PathVariable Long customerId) {

        return customerRepository
                .findById(customerId)
                .map(c -> new ResponseEntity<>(
                        new GetCustomerResponse(c.getId(), c.getName()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
