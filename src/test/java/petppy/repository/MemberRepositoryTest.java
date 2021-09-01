/*
package petppy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.member.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Commit
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        Member member = addMember();

        Member findMember = memberRepository.getById(member.getId());

        Assertions.assertEquals(findMember.getId(), member.getId());
        Assertions.assertEquals(findMember.getPassword(), member.getPassword());
        Assertions.assertEquals(findMember.getName(), member.getName());

        System.out.println("findMember = " + findMember);
    }

    @Test
    public void 회원정보_변경() throws Exception {
        //given
        Member member = addMember();

        em.flush();
        em.clear();
        //when
        Member findMember = memberRepository.findById(member.getId()).get();

        findMember.changeAddress(Address
                .builder()
                .city("modifiedCity")
                .street("modifiedStreet")
                .zipcode("modifiedZipcode")
                .build());
        //then
        System.out.println("findMember = " + findMember);
    }

    private Member addMember() {
        //given
        Member member = Member.builder()
                .id("aaa")
                .password("bbb")
                .name("hhh")
                .address(Address.builder().city("111").street("222").zipcode("333").build())
                .build();

        memberRepository.save(member);
        return member;
    }
}*/
