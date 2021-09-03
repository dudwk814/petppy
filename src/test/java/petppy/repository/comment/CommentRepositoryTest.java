package petppy.repository.comment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Board;
import petppy.domain.Comment;
import petppy.domain.user.User;
import petppy.repository.BoardRepository;
import petppy.repository.UserRepository;

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

        Comment findComment = commentRepository.getById(34L);

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

}