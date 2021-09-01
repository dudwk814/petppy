/*
package petppy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.Board;
import petppy.domain.member.Member;
import petppy.dto.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.repository.BoardRepository;
import petppy.repository.MemberRepository;

import javax.persistence.EntityManager;

import java.util.List;

@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 게시글_등록() throws Exception {
        //given
        Member member = addMember();

        em.flush();
        em.clear();

        //when
        BoardDto boardDto = BoardDto.builder()
                .memberId(member.getId())
                .title("testTitle")
                .content("testContent")
                .build();

        boardService.createBoard(boardDto);

    }

    @Test
    @Rollback(value = false)
    public void 게시글_수정() throws Exception {
        //given
        Member member = addMember();

        em.flush();
        em.clear();

        Board board = addBoard(member);

        //when
        BoardDto boardDto = BoardDto
                .builder()
                .boardId(board.getId())
                .title("modifiedTitle")
                .content("modifiedContent")
                .build();


        //when

        boardService.modifyBoard(boardDto);

        //then
    }

    @Test
    public void 게시글_검색() throws Exception {
        //given


            Member member = addMember();

            em.flush();
            em.clear();

        for (int i = 0; i < 100; i ++) {

            addBoard(member);
        }

        PageRequestDTO requestDTO = PageRequestDTO
                .boardSearchBuilder()
                .keyword("test")
                .type("tc")
                .page(1)
                .size(10)
                .build();

        //when
        PageResultDTO<BoardDto, Board> result = boardService.searchBoardList(requestDTO);

        List<BoardDto> findBoardList = result.getDtoList();

        //then
        for (BoardDto boardDto : findBoardList) {
            System.out.println("boardDto = " + boardDto);
        }
    }

    @Test
    public void 게시물_조회() throws Exception {
        //given
        Member member = addMember();

        em.flush();
        em.clear();

        for (int i = 0; i < 100; i ++) {

            addBoard(member);
        }
        //when
        BoardDto boardDto = boardService.searchBoard(100L);

        //then
        System.out.println("boardDto = " + boardDto);
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

    private Board addBoard(Member member) {
        Board board = Board
                .builder()
                .title("testTitle")
                .content("testContent")
                .member(member)
                .build();

        boardRepository.save(board);
        return board;
    }


}*/
