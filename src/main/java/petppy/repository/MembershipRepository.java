package petppy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.user.User;
import petppy.domain.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    public Membership findByUser(User user);

}
