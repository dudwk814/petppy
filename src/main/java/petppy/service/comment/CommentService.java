package petppy.service.comment;

import petppy.domain.comment.Comment;
import petppy.dto.comment.CommentDTO;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;

import java.util.List;

public interface CommentService {

    public CommentDTO createComment(CommentDTO commentDTO);

    public CommentDTO findComment(Long commentId);

    public List<CommentDTO> findCommentsByBoardId(Long boardId);

    public void deleteComment(CommentDTO dto);

    public void modifyComment(CommentDTO dto);

    public PageResultDTO<CommentDTO, Comment> findCommentByBoardIdWithPaging(Long boardId, PageRequestDTO requestDTO);

    public List<CommentDTO> findCommentByParent(Long parentId);

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
                    .childrenCount(comment.getChildrenCount())
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
                    .childrenCount(comment.getChildrenCount())
                    .build();
        }
    }



}
