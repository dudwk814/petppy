package petppy.repository.review;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
