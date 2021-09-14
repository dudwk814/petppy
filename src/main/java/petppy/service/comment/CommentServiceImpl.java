package petppy.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Board;
import petppy.domain.Comment;
import petppy.domain.DeleteStatus;
import petppy.dto.BoardDto;
import petppy.dto.CommentDTO;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.exception.BoardNotFoundException;
import petppy.exception.CommentNotFoundException;
import petppy.exception.UserNotFoundException;
import petppy.repository.BoardRepository;
import petppy.repository.UserRepository;
import petppy.repository.comment.CommentRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * Comment 생성
     */
    @Override
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {

        Board board = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Comment comment = Comment
                .builder()
                .board(board)
                .user(userRepository.findByEmail(commentDTO.getEmail()).orElseThrow(UserNotFoundException::new))
                .content(commentDTO.getContent())
                .parent(
                        commentDTO.getParentId() != null ? commentRepository.findById(commentDTO.getParentId()).orElseThrow(CommentNotFoundException::new) : null
                )
                .build();

        Comment savedComment = commentRepository.save(comment); // comment 생성

        if (commentDTO.getParentId() != null) {
            commentRepository.findById(commentDTO.getParentId()).orElseThrow(CommentNotFoundException::new).plusChildrenCount();
        }

        board.plusCommentCount();   // 댓글 수 + 1

        return entityToDTO(savedComment);
    }


    /**
     * Comment 수정
     */
    @Override
    @Transactional
    public void modifyComment(CommentDTO dto) {
        Comment findComment = commentRepository.findById(dto.getId()).orElseThrow(CommentNotFoundException::new);

        findComment.changeComment(dto.getContent());

    }

    @Override
    public PageResultDTO<CommentDTO, Comment> findCommentByBoardIdWithPaging(Long boardId, PageRequestDTO requestDTO) {

        Page<Comment> result = commentRepository.findCommentByBoardIdWithPaging(boardId, requestDTO);

        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();

        Function<Comment, CommentDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
    }

    /**
     * Comment 삭제
     */
    @Override
    @Transactional
    public void deleteComment(CommentDTO commentDTO) {

        Comment comment = commentRepository.findCommentByIdWithParent(commentDTO.getId()).orElseThrow(CommentNotFoundException::new);
        if(comment.getChildren().size() != 0) { // 자식이 있으면 상태만 변경
            comment.changeDeletedStatus(DeleteStatus.Y);
        } else { // 삭제 가능한 조상 댓글을 구해서 삭제
            commentRepository.delete(comment);
            Board board = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(BoardNotFoundException::new);
            board.minusCommentCount();

            if (comment.getParent() != null) {
                comment.getParent().minusChildrenCount();
            }
        }

    }

   /* *//**
     * 삭제 가능한 Comment 조회
     *//*
    private Comment getDeletableAncestorComment(Comment comment) { // 삭제 가능한 조상 댓글을 구함
        Comment parent = comment.getParent(); // 현재 댓글의 부모를 구함
        if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == DeleteStatus.Y)
            // 부모가 있고, 부모의 자식이 1개(지금 삭제하는 댓글)이고, 부모의 삭제 상태가 TRUE인 댓글이라면 재귀
            return getDeletableAncestorComment(parent);
        return comment; // 삭제해야하는 댓글 반환
    }*/

    /**
     * Comment 단건 조회
     */
    @Override
    public CommentDTO findComment(Long commentId) {

        Comment findComment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        return entityToDTO(findComment);
    }

    /**
     * Comment LIST 조회
     */
    @Override
    public List<CommentDTO> findCommentsByBoardId(Long boardId) {

        boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
        return convertNestedStructure(commentRepository.findCommentByBoardId(boardId));
    }


    /**
     * 조회한 Comment DTO LIST를 중첩구조로 변환
     */
    private List<CommentDTO> convertNestedStructure(List<Comment> comments) {
        List<CommentDTO> result = new ArrayList<>();
        Map<Long, CommentDTO> map = new HashMap<>();
        comments.stream().forEach(comment -> {
            CommentDTO dto = entityToDTO(comment);
            map.put(dto.getId(), dto);
            if(comment.getParent() != null) {
                map.get(comment.getParent().getId()).getChildren().add(dto);
            } else {
                result.add(dto);
            }
        });
        return result;
    }
}
