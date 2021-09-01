/*
package petppy.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.*;
import petppy.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static petppy.domain.Rating.BUSINESS;

@SpringBootTest
@Transactional
class MembershipRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 멤버십_등록() throws Exception {
        //given
        Member member = addMember();

        Membership membership = addMembership(member);

        //when
        Membership findMembership = membershipRepository.findById(membership.getId()).get();

        //then
        assertThat(findMembership).isEqualTo(membership);
        assertThat(findMembership.getRating()).isEqualTo(membership.getRating());

        System.out.println("findMembership.id = " + findMembership.getId());
        System.out.println("findMembership.rating = " + findMembership.getRating());

    }

    @Test
    public void 멤버십_변경() throws Exception {
        //given

        Member member = addMember();

        Membership membership = addMembership(member);

        em.flush();
        em.clear();

        System.out.println("membership = " + membership);

        //when
        membership.changeRating(BUSINESS);

        //then
        System.out.println("membership = " + membership);
    }

    private Member addMember() {
        Member member = Member.builder()
                .id("aaa")
                .password("bbb")
                .name("hhh")
                .address(Address.builder().city("111").street("222").zipcode("333").build())
                .build();

        memberRepository.save(member);
        return member;
    }

    public Membership addMembership(Member member) {
        Membership membership = Membership
                .builder()
                .member(member)
                .build();

        membershipRepository.save(membership);

        return membership;

    }



}*/
