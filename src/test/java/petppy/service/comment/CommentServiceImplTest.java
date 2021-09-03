package petppy.service.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.dto.CommentDTO;
import petppy.repository.BoardRepository;
import petppy.repository.comment.CommentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @Commit
    public void 댓글_등록() throws Exception {
        //given
        CommentDTO commentDTO = CommentDTO
                .builder()
                .userId(1L)
                .content("test comment")
                .boardId(3L)
                .email("kj99658103@gmail.com")
                .build();
        //when
        commentService.createComment(commentDTO);

        //then
        assertEquals(1, boardRepository.getById(3L).getCommentCount()); // 댓글 등록시 Board의 댓글 수가 1증가해야함
    }

    @Test
    public void 댓글_목록_조회() throws Exception {
        List<CommentDTO> result = commentService.findCommentsByBoardId(3L);

        for (CommentDTO commentDTO : result) {
            System.out.println("commentDTO = " + commentDTO);
        }

        // result(34번글)의 수, 1개가 나와야함
        assertEquals(1, result.size());

        // result(34번글)의 child(35, 37번 글)의 수, 2개가 나와야함
        assertEquals(2, result.get(0).getChildren().size());

    }

    @Test
    @Commit
    public void 댓글_삭제() throws Exception {
        commentService.deleteComment(35L);


        assertFalse(commentRepository.findById(35L).isPresent());

    }
}