/*
package petppy.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.Board;
import petppy.domain.member.Member;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void insertDummies() {
        Member member = Member.builder()
                .id("aaa")
                .password("bbb")
                .name("hhh")
                .address(Address.builder().city("111").street("222").zipcode("333").build())
                .build();

        memberRepository.save(member);

        for (int i = 0; i < 100; i++) {
            Member findMember = memberRepository.findById("aaa").get();

            Board board = Board
                    .builder()
                    .member(findMember)
                    .content("testContent" + i)
                    .title("testTitle" + i)
                    .build();

            boardRepository.save(board);
        }
    }

    @Test
    public void 게시글_등록() throws Exception {
        //given

        Member findMember = memberRepository.findById("aaa").get();

        Board board = Board
                .builder()
                .member(findMember)
                .content("testContent")
                .title("testTitle")
                .build();

        boardRepository.save(board);

        em.flush();
        em.clear();

        //when

        Board findBoard = boardRepository.findById(board.getId()).get();

        //then
        assertThat(findBoard.getContent()).isEqualTo("testContent");
        assertThat(findBoard.getTitle()).isEqualTo("testTitle");

        System.out.println("findBoard = " + findBoard);
    }

    @Test
    public void 댓글_변경() throws Exception {
        Optional<Board> result = boardRepository.findById(10L);

        if (result.isPresent()) {
            Board findBoard = result.get();
            findBoard.changeBoard("modifiedTitle", "modifiedContent");

            em.flush();
            em.clear();

            assertThat(findBoard.getTitle()).isEqualTo("modifiedTitle");
            assertThat(findBoard.getContent()).isEqualTo("modifiedContent");

        } else {
            fail();
        }

    }

    @Test
    public void 글_삭제() throws Exception {
        //when
        boardRepository.deleteById(99L);

        List<Board> result = boardRepository.findAll();

        Optional<Board> findBoard = boardRepository.findById(99L);

        //then
        assertTrue(!findBoard.isPresent()); // findBoard는 삭제했으므로 존재X
        assertThrows(NoSuchElementException.class, () -> findBoard.get());  // NoSuchElementException 터지면 안됨
        assertThat(result.size()).isEqualTo(99);    // 100개 중 한개를 삭제하면 결과는 99개 나와야함
    }
}*/
