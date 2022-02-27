package petppy.repository.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petppy.domain.comment.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CustomCommentRepository{

    @Query("select c from Comment c left join fetch c.parent where c.id = :id")
    Optional<Comment> findCommentByIdWithParent(@Param("id") Long id);

    @Query("select c from Comment c where c.parent.id = :parentId")
    List<Comment> findCommentByParent(@Param("parentId") Long parentId);

    @Modifying
    @Query("delete from Comment c where c.board.id =:id")
    void deleteByBoardId(@Param("id") Long id);

    void deleteByUserId(Long id);


}
