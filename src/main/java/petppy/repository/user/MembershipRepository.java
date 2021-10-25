package petppy.repository.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petppy.domain.user.Rating;
import petppy.domain.user.User;
import petppy.domain.user.Membership;

import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    public Membership findByUser(User user);


    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    @Override
    List<Membership> findAll();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Membership m set m.rating = :rating, m.dogWalkCount = 0, m.petGroomingCount = 0, m.vetVisit = 0 where m.id in :ids")
    public void changeRatingToNone(@Param("rating")Rating rating, @Param("ids") List<Long> ids);

    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    public List<Membership> findByRatingNot(Rating rating);
}
