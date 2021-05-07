package de.mk.learning.rxjava.rxjavademo.reactor.configuration.routing;

import de.mk.learning.rxjava.rxjavademo.reactor.configuration.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;


@Configuration
public class CustomerRouter {

    @Bean
    public RouterFunction<ServerResponse> route(CustomerHandler handler) {
        return RouterFunctions.route(
//                GET("/customers").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                GET("/customers").and(RequestPredicates.accept(new MediaType("application", "stream+json"))),
                handler::getAllCustomers)
                .andRoute(
                        GET("/customer/id/{cid}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        handler::getCustomerById)
                .andRoute(
                        GET("memory"),
                        handler::getMemory);

    }
}
