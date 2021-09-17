/*
package petppy.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.board.Board;
import petppy.domain.member.Member;
import petppy.domain.Reply;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class ReplyRepositoryTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void insertDummies() {

        
    }

    @Test
    public void 댓글_등록() throws Exception {

        //given

        Member member = addMember();

        memberRepository.save(member);

        Board board = Board
                .builder()
                .member(member)
                .content("testContent")
                .title("testTitle")
                .build();

        boardRepository.save(board);

        Reply reply = addReply(member, board);

        em.flush();
        em.clear();
        //when
        Reply findReply = replyRepository.findById(reply.getId()).get();

        //then
        System.out.println("findReply = " + findReply);

        System.out.println("findReply.getMember().getName() = " + findReply.getMember().getName());
        System.out.println("findReply.getBoard().getTitle() = " + findReply.getBoard().getTitle());
    }

    @Test
    public void 댓글_내용_변경() throws Exception {

        //given

        Member member = addMember();

        memberRepository.save(member);

        Board board = Board
                .builder()
                .member(member)
                .content("testContent")
                .title("testTitle")
                .build();

        boardRepository.save(board);

        Reply reply = addReply(member, board);

        Reply findReply = replyRepository.findById(reply.getId()).get();

        //when
        findReply.changeReply("modifiedContent");

        em.clear();
        em.flush();

        //then
        System.out.println("findReply = " + findReply);
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

    public Reply addReply(Member member, Board board) {
        Reply reply = Reply
                .builder()
                .member(member)
                .content("testReplyContent")
                .board(board)
                .build();

        replyRepository.save(reply);

        return reply;
    }

}*/
