package de.mk.learning.rxjava.rxjavademo.reactor.configuration.routing;

import de.mk.learning.rxjava.rxjavademo.reactor.configuration.handler.NoteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class NoteRouter {

    @Bean
    public RouterFunction<ServerResponse> routeNote(NoteHandler handler) {
        return RouterFunctions.route(
                GET("/notes").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::getAllNotes);

    }
}
