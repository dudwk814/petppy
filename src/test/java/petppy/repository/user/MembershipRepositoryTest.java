package petppy.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.user.Membership;
import petppy.domain.user.Rating;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MembershipRepositoryTest {

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    EntityManager em;

    @Test
    @Commit
    public void 멤버십_등급_변경() throws Exception {
        Membership membership = membershipRepository.findById(2L).get();

        membership.changeRating(Rating.PERSONAL);

        em.flush();
        em.clear();

        Membership findMembership = membershipRepository.findById(2L).get();

        assertEquals(3, findMembership.getDogWalkCount());
        assertEquals(1, findMembership.getPetGroomingCount());
        assertEquals(1, findMembership.getVetVisit());
    }
}