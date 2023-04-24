package rocks.zipcode.bugz2.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.bugz2.domain.Attachment;

/**
 * Spring Data JPA repository for the Attachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {}
