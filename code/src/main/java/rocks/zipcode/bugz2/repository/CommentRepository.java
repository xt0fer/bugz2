package rocks.zipcode.bugz2.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.bugz2.domain.Comment;

/**
 * Spring Data JPA repository for the Comment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select comment from Comment comment where comment.login.login = ?#{principal.username}")
    List<Comment> findByLoginIsCurrentUser();
}
