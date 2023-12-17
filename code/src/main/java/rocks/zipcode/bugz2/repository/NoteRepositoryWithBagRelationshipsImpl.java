package rocks.zipcode.bugz2.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import rocks.zipcode.bugz2.domain.Note;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class NoteRepositoryWithBagRelationshipsImpl implements NoteRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Note> fetchBagRelationships(Optional<Note> note) {
        return note.map(this::fetchTickets);
    }

    @Override
    public Page<Note> fetchBagRelationships(Page<Note> notes) {
        return new PageImpl<>(fetchBagRelationships(notes.getContent()), notes.getPageable(), notes.getTotalElements());
    }

    @Override
    public List<Note> fetchBagRelationships(List<Note> notes) {
        return Optional.of(notes).map(this::fetchTickets).orElse(Collections.emptyList());
    }

    Note fetchTickets(Note result) {
        return entityManager
            .createQuery("select note from Note note left join fetch note.tickets where note is :note", Note.class)
            .setParameter("note", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Note> fetchTickets(List<Note> notes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, notes.size()).forEach(index -> order.put(notes.get(index).getId(), index));
        List<Note> result = entityManager
            .createQuery("select distinct note from Note note left join fetch note.tickets where note in :notes", Note.class)
            .setParameter("notes", notes)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
