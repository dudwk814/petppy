package petppy.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository{

}
