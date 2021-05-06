package de.mk.learning.rxjava.rxjavademo.reactor.configuration.handler;

import de.mk.learning.rxjava.rxjavademo.data.model.Customer;
import de.mk.learning.rxjava.rxjavademo.data.model.Notes;
import de.mk.learning.rxjava.rxjavademo.data.repository.CustomerRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NoteHandler {

    private final CustomerRepository customerRepository;

    public NoteHandler(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Mono<ServerResponse> getAllNotes(ServerRequest request) {
        Flux<Notes> notes = this.customerRepository.getAllNotes();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(notes, Customer.class);
    }

}
