package de.mk.learning.rxjava.rxjavademo.reactor.configuration.handler;

import de.mk.learning.rxjava.rxjavademo.data.model.Customer;
import de.mk.learning.rxjava.rxjavademo.data.repository.CustomerRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

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

    public Mono<ServerResponse> getMemory(ServerRequest request) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        String memoryUsage = String.format("Initial memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getInit() / 1073741824);
        String heapUsage = String.format("Used heap memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getUsed() / 1073741824);
        String maxHeap = String.format("Max heap memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getMax() / 1073741824);
        String commitedMemory = String.format("Committed memory: %.2f GB", (double) memoryMXBean.getHeapMemoryUsage().getCommitted() / 1073741824);

        String output = memoryUsage + System.lineSeparator() + heapUsage + System.lineSeparator() + maxHeap + System.lineSeparator() + commitedMemory;

        return ServerResponse.ok().bodyValue(output);


    }

}
