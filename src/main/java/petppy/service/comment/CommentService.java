package petppy.service.comment;

import petppy.domain.Comment;
import petppy.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    public void createComment(CommentDTO commentDTO);

    public List<CommentDTO> findCommentsByBoardId(Long boardId);

    public void deleteComment(Long commentId);

    default CommentDTO entityToDTO(Comment comment) {

        if (comment.getParent() != null) {
            return CommentDTO
                    .builder()
                    .content(comment.getContent())
                    .email(comment.getUser().getEmail())
                    .boardId(comment.getBoard().getId())
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .parentId(comment.getParent().getId())
                    .build();
        } else {
            return CommentDTO
                    .builder()
                    .content(comment.getContent())
                    .email(comment.getUser().getEmail())
                    .boardId(comment.getBoard().getId())
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .build();
        }

    }

}
