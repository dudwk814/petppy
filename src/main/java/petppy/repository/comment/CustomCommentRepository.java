package petppy.repository.comment;

import org.springframework.data.domain.Page;
import petppy.domain.Comment;
import petppy.dto.PageRequestDTO;

import java.util.List;

public interface CustomCommentRepository {

    //findCommentsByBoardIdWithParentOrderByParentIdAscNullsFirstCreatedAtAsc
    List<Comment> findCommentByBoardId(Long boardId);

    Page<Comment> findCommentByBoardIdWithPaging(Long boardId, PageRequestDTO requestDTO);
}
