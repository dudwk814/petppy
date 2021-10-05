package petppy.service.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.user.User;
import petppy.dto.board.BoardDto;
import petppy.repository.board.BoardRepository;
import petppy.repository.user.UserRepository;
import petppy.service.board.BoardService;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    UserRepository userRepository;

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
        User user = userRepository.findById(5L).get();

        em.flush();
        em.clear();

        //when
        for (int i = 0; i < 1000; i++) {
            BoardDto boardDto = BoardDto.builder()
                    .email(user.getEmail())
                    .title("testTitle" + i)
                    .content("testContent" + i)
                    .build();

            boardService.createBoard(boardDto);
        }


    }

    /*@Test
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
    }*/



}
