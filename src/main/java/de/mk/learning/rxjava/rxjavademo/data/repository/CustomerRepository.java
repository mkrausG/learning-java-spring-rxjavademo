package de.mk.learning.rxjava.rxjavademo.data.repository;


import de.mk.learning.rxjava.rxjavademo.data.model.Customer;
import de.mk.learning.rxjava.rxjavademo.data.model.Notes;
import io.reactivex.Flowable;
import org.davidmoten.rx.jdbc.Database;
import org.davidmoten.rx.jdbc.pool.DatabaseType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Component
public class CustomerRepository {

    private Database db;

    public CustomerRepository() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:C:/Projekte/Learning/Spring/rxjava/db/Customer.db;MV_STORE=false");

//        NonBlockingConnectionPool pool =
//                Pools.nonBlocking()
//                        .maxPoolSize(Runtime.getRuntime().availableProcessors() * 5)
//                        .connectionProvider(ConnectionProvider.from(connection))
//                        .build();

        //this.db = Database.from(pool);
        // TODO: Das ggf dann noch raus extrahieren da wir einen Pool nutzen... den können wir dann in anderen Repositories nutzen :-)
        this.db = Database.nonBlocking()
                .url("jdbc:h2:C:/Projekte/Learning/Spring/rxjava/db/Customer.db;MV_STORE=false")
                .maxIdleTime(30, TimeUnit.MINUTES)
                .healthCheck(DatabaseType.H2)
                .idleTimeBeforeHealthCheck(5, TimeUnit.SECONDS)
                .maxPoolSize(3)
                .build();
    }

    // TODO: AUch hier eigentlich Entity und DTO trennen :-)
    public Flux<Customer> getAllEmployees() {
        //language=H2
        String query = "SELECT * from CUSTOMER";

        Flowable<Customer> customerFlowable =
                // DB Liest row für row und Flowable gibt dann diese Row d.h. Customer als Stream weiter :-) das geht dann an den Controller
                this.db.select(query)
                        .get(
                                rs -> {
                                    Customer customer = new Customer();
                                    customer.setId(rs.getLong("C_ID"));
                                    customer.setCodebar(rs.getString("CODEBAR"));
                                    customer.setDescription(rs.getString("DESCRIPTION"));
                                    customer.setFirstName(rs.getString("FIRST_NAME"));
                                    customer.setLastName(rs.getString("LAST_NAME"));
                                    // TODO: DAs später customer.setNotes()

                                    return customer;
                                });

        // Convert the Flowable into a Flux :-) that will do the trick
        return Flux.from(customerFlowable);
    }

    public Mono<Customer> getEmployeeById(long customerId) {
        String query = "SELECT * from CUSTOMER WHERE C_ID = :cid";

        Flowable<Customer> customerFlowable =
                this.db.select(query)
                        .parameter("cid", customerId)
                        .get(rs -> {
                            Customer customer = new Customer();
                            customer.setId(rs.getLong("C_ID"));
                            customer.setCodebar(rs.getString("CODEBAR"));
                            customer.setDescription(rs.getString("DESCRIPTION"));
                            customer.setFirstName(rs.getString("FIRST_NAME"));
                            customer.setLastName(rs.getString("LAST_NAME"));
                            // TODO: DAs später customer.setNotes()

                            return customer;
                        });

        // Convert the "Reactive List" e.g Flowable into a mono since we only return one entry
        return Mono.from(customerFlowable);
    }

    // TODO: Das gehört dann in eigene Repository Klasse, wenn DB heraus refakturiert ist
    public Flux<Notes> getAllNotes() {
        //language=H2
        String query = "SELECT * from NOTES";

        Flowable<Notes> notesFlowable =
                // DB Liest row für row und Flowable gibt dann diese Row d.h. Customer als Stream weiter :-) das geht dann an den Controller
                this.db.select(query)
                        .get(
                                rs -> {
                                    Notes note = new Notes();
                                    note.setId(rs.getLong("N_ID"));
                                    note.setNote(rs.getString("NOTE"));

                                    return note;
                                });

        // Convert the Flowable into a Flux :-) that will do the trick
        return Flux.from(notesFlowable);
    }
}
