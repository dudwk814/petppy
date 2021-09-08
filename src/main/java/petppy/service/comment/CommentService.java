package petppy.service.comment;

import petppy.domain.Comment;
import petppy.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    public CommentDTO createComment(CommentDTO commentDTO);

    public CommentDTO findComment(Long commentId);

    public List<CommentDTO> findCommentsByBoardId(Long boardId);

    public void deleteComment(CommentDTO dto);

    public void modifyComment(CommentDTO dto);

    default CommentDTO entityToDTO(Comment comment) {   // 부모 comment의 id값 유무에 따른 builder 분기

        if (comment.getParent() != null) {
            return CommentDTO
                    .builder()
                    .content(comment.getContent())
                    .email(comment.getUser().getEmail())
                    .boardId(comment.getBoard().getId())
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .parentId(comment.getParent().getId())
                    .createdDate(comment.getCreatedDate())
                    .lastModifiedDate(comment.getModifiedDate())
                    .build();
        } else {
            return CommentDTO
                    .builder()
                    .content(comment.getContent())
                    .email(comment.getUser().getEmail())
                    .boardId(comment.getBoard().getId())
                    .id(comment.getId())
                    .userId(comment.getUser().getId())
                    .createdDate(comment.getCreatedDate())
                    .lastModifiedDate(comment.getModifiedDate())
                    .build();
        }
    }



}
