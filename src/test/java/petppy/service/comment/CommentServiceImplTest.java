package petppy.service.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.comment.Comment;
import petppy.dto.comment.CommentDTO;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.repository.board.BoardRepository;
import petppy.repository.comment.CommentRepository;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    EntityManager em;

    @Test
    @Commit
    public void 댓글_등록() throws Exception {
        //given
        for (int i = 0; i < 100; i++) {
            CommentDTO commentDTO = CommentDTO
                    .builder()
                    .userId(1L)
                    .content("test comment" + i)
                    .boardId(2088L)
                    .email("kj99658103@gmail.com")
                    .build();
            commentService.createComment(commentDTO);
        }

    }

    @Test
    public void 댓글_목록_조회() throws Exception {
        List<CommentDTO> result = commentService.findCommentsByBoardId(3L);

        for (CommentDTO commentDTO : result) {
            System.out.println("commentDTO = " + commentDTO);
        }

        // result(34번글)의 수, 1개가 나와야함
        /*assertEquals(1, result.size());*/

        // result(34번글)의 child(35, 37번 글)의 수, 2개가 나와야함
        /*assertEquals(2, result.get(0).getChildren().size());*/

    }

    @Test
    public void 댓글_목록_조회_페이징() throws Exception {
        //given
        PageRequestDTO requestDTO = new PageRequestDTO();
        PageResultDTO<CommentDTO, Comment> result = commentService.findCommentByBoardIdWithPaging(2088L, requestDTO);
        //when

        List<CommentDTO> content = result.getDtoList();

        for (CommentDTO commentDTO : content) {
            System.out.println("commentDTO = " + commentDTO);
        }

        //then
    }

    @Test
    public void 하위_댓글_조회() throws Exception {
        //given
        List<CommentDTO> commentByParent = commentService.findCommentByParent(51L);
        //when

        for (CommentDTO commentDTO : commentByParent) {
            System.out.println("commentDTO = " + commentDTO);
        }

        //then
    }

    /*@Test
    @Commit
    public void 댓글_삭제() throws Exception {
        commentService.deleteComment(35L);


        assertFalse(commentRepository.findById(35L).isPresent());

    }*/

    /*@Test
    @Commit
    public void 댓글_수정() throws Exception {
        CommentDTO commentDTO = CommentDTO.builder().id(1L).content("zzzz").build();


        commentService.modifyComment(commentDTO);

    }*/
}