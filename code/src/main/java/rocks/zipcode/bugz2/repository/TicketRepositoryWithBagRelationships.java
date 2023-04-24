package rocks.zipcode.bugz2.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import rocks.zipcode.bugz2.domain.Ticket;

public interface TicketRepositoryWithBagRelationships {
    Optional<Ticket> fetchBagRelationships(Optional<Ticket> ticket);

    List<Ticket> fetchBagRelationships(List<Ticket> tickets);

    Page<Ticket> fetchBagRelationships(Page<Ticket> tickets);
}
