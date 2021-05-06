package de.mk.learning.rxjava.rxjavademo.reactor.configuration.handler;

import de.mk.learning.rxjava.rxjavademo.data.model.Customer;
import de.mk.learning.rxjava.rxjavademo.data.repository.CustomerRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final CustomerRepository customerRepository;

    public CustomerHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Mono<ServerResponse> getAllCustomers(ServerRequest request) {
        Flux<Customer> customers = this.customerRepository.getAllEmployees();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers, Customer.class);
    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request) {
        Long customerId = Long.valueOf(request.pathVariable("cid"));

        Mono<Customer> customer = this.customerRepository.getEmployeeById(customerId);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customer, Customer.class);
    }

}
