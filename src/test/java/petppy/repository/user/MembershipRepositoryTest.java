package petppy.repository.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.user.Membership;
import petppy.domain.user.Rating;
import petppy.dto.user.MembershipCountDTO;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    @Commit
    public void 멤버십_등급_변경_벌크연산() throws Exception {
        //given
        List<Membership> result = membershipRepository.findAll();
        List<Long> membershipIds = new ArrayList<>();

        for (Membership membership : result) {
            membershipIds.add(membership.getId());
        }

        membershipRepository.changeRatingToNone(Rating.NONE, membershipIds);

    }
    
    @Test
    public void 기한지난_멤버십_조회() throws Exception {
        /*Membership membership = membershipRepository.findById(2L).get();

        System.out.println("membership.getModifiedDate() = " + membership.getModifiedDate());
        System.out.println("membership.getModifiedDate() = " + membership.getModifiedDate().plusDays(30));*/

        List<Membership> result = membershipRepository.findByRatingNot(Rating.NONE);

        System.out.println("result.size() = " + result.size());

    }

    @Test
    public void 멤버십별_회원수_카운트트() throws Exception {
        List<MembershipCountDTO> membershipCountDTOS = membershipRepository.countByMembershipWithRating();

        for (MembershipCountDTO membershipCountDTO : membershipCountDTOS) {
            System.out.println("membershipCountDTO = " + membershipCountDTO);
            System.out.println("membershipCountDTO.getRating().toString() = " + membershipCountDTO.getRating().toString());
        }
    }
}