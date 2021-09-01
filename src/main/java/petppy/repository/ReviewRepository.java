package petppy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
