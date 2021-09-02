package petppy.repository.comment;

import petppy.domain.Comment;

import java.util.List;

public interface CustomCommentRepository {

    //findCommentsByBoardIdWithParentOrderByParentIdAscNullsFirstCreatedAtAsc
    List<Comment> findCommentByBoardId(Long boardId);
}
