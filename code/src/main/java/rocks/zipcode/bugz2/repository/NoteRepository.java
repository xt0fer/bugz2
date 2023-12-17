package rocks.zipcode.bugz2.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rocks.zipcode.bugz2.domain.Note;

/**
 * Spring Data JPA repository for the Note entity.
 *
 * When extending this class, extend NoteRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface NoteRepository extends NoteRepositoryWithBagRelationships, JpaRepository<Note, Long> {
    @Query("select note from Note note where note.user.login = ?#{principal.username}")
    List<Note> findByUserIsCurrentUser();

    default Optional<Note> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Note> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Note> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct note from Note note left join fetch note.user",
        countQuery = "select count(distinct note) from Note note"
    )
    Page<Note> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct note from Note note left join fetch note.user")
    List<Note> findAllWithToOneRelationships();

    @Query("select note from Note note left join fetch note.user where note.id =:id")
    Optional<Note> findOneWithToOneRelationships(@Param("id") Long id);
}
