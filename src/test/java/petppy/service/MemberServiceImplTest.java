/*
package petppy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.member.Member;
import petppy.domain.user.Rating;
import petppy.dto.MemberDto;
import petppy.repository.MemberRepository;
import petppy.repository.user.MembershipRepository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    EntityManager em;


    @BeforeEach
    public void insertDummies() {

        for (int i = 0; i < 100; i++) {

            Member member = Member.builder()
                    .id("memberId" + i)
                    .password("password" + i)
                    .name("member" + i)
                    .address(Address.builder().city("111").street("222").zipcode("333").build())
                    .build();

            MemberDto memberDto = memberService.entityToDto(member);

            memberService.joinedMember(memberDto);
        }
    }

    @Test
    @Rollback(value = false)
    public void 회원_id_단건_조회() throws Exception {

        String id = "memberId55";

        //when
        MemberDto findMember = memberService.findOne(id);

        //then
        System.out.println("findMember = " + findMember);
        assertThat(findMember.getName()).isEqualTo("member55");
        assertThat(findMember.getId()).isEqualTo(id);
    }

    @Test
    public void 회원_이름_조회() throws Exception {
        //given
        String name = "member";

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<MemberDto> members = memberService.findMemberListByName(name, pageRequest);

        //then
        for (MemberDto member : members) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void 전체_회원_조회() throws Exception {
        //given

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name").descending());


        //when
        List<MemberDto> members = memberService.findAll(pageRequest);

        //then
        assertThat(members.size()).isEqualTo(10);
    }

    @Test
    @Rollback(value = false)
    public void 회원_주소_수정() throws Exception {
        //given
        MemberDto findMember = memberService.findOne("memberId55");

        System.out.println("findMember = " + findMember);
        System.out.println("=================================");

        //when
        findMember.setCity("test1");
        findMember.setStreet("test2");
        findMember.setZipcode("test3");

        memberService.ModifyMemberAddress(findMember);


        em.flush();
        em.clear();

        //then
        MemberDto result = memberService.findOne("memberId55");
        System.out.println("result = " + result);
    }

    @Test
    public void 회원_삭제() throws Exception {

        //given
        memberService.deleteMember("memberId0");

        //when
        Optional<Member> findMember = memberRepository.findById("memberId0");


        //then
        assertTrue(!findMember.isPresent());
    }

    @Test
    public void 회원_중복_확인() throws Exception {
        //given

        //when
        boolean exist1 = memberService.checkMemberIdExist("memberId0");
        boolean exist2 = memberService.checkMemberIdExist("memberId101");

        //then

        assertTrue(exist1);
        assertFalse(exist2);

        System.out.println("exist = " + exist1);
        System.out.println("exist2 = " + exist2);
    }


    @Test
    @Rollback(value = false)
    public void 멤버십_변경() throws Exception {
        //given

        Rating rating = Rating.BUSINESS;

        Member findMember = memberRepository.findById("memberId0").get();

        MemberDto dto = memberService.entityToDto(findMember);
        //when
        memberService.changeMembership(dto, rating);

        //then
    }
}
*/
