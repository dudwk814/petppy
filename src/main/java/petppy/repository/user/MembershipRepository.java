package petppy.repository.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.user.User;
import petppy.domain.user.Membership;

import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    public Membership findByUser(User user);


    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    @Override
    List<Membership> findAll();
}
