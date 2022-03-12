package petppy.repository.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.board.Board;
import petppy.domain.comment.Comment;
import petppy.domain.user.User;
import petppy.repository.board.BoardRepository;
import petppy.repository.user.UserRepository;

import java.util.List;


@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    @Commit
    public void 댓글_등록() throws Exception {

        //given
        User findUser = userRepository.findByEmail("kj99658103@gmail.com").get();

        Board findBoard = boardRepository.getById(3L);

        Comment comment = Comment
                .builder()
                .board(findBoard)
                .user(findUser)
                .content(new String("test_comment"))
                .build();
        //when
        commentRepository.save(comment);

        //then
    }

    @Test
    @Commit
    public void 대댓글_등록() throws Exception {
        //given
        User findUser = userRepository.findByEmail("kj99658103@gmail.com").get();

        Board findBoard = boardRepository.getById(3L);

        Comment findComment = commentRepository.getById(38L);

        Comment comment = Comment
                .builder()
                .user(findUser)
                .board(findBoard)
                .parent(findComment)
                .content("test_comment2")
                .build();

        //when

        commentRepository.save(comment);

        //then
    }

    @Test
    public void 댓글_조회() throws Exception {
        //given
        List<Comment> result = commentRepository.findCommentByBoardId(3L);
        //when
        for (Comment comment : result) {
            System.out.println("comment = " + comment);
        }

    }

    @Test
    @Commit
    public void 특정_게시글_대댓글_지우기() throws Exception {
        Long id = 44L;

        commentRepository.deleteByBoardIdAndParentNotNull(id);
    }

    @Test
    @Commit
    public void 회원번호로_댓글삭제() throws Exception {
        //given
        User findUser = userRepository.findByEmail("admin@123.com").get();

        commentRepository.deleteByUserId(findUser.getId());
    }

}