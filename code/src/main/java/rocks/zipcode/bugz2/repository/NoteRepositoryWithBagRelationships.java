package rocks.zipcode.bugz2.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import rocks.zipcode.bugz2.domain.Note;

public interface NoteRepositoryWithBagRelationships {
    Optional<Note> fetchBagRelationships(Optional<Note> note);

    List<Note> fetchBagRelationships(List<Note> notes);

    Page<Note> fetchBagRelationships(Page<Note> notes);
}
