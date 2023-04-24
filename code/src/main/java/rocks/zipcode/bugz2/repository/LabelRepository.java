package rocks.zipcode.bugz2.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import rocks.zipcode.bugz2.domain.Label;

/**
 * Spring Data JPA repository for the Label entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {}
