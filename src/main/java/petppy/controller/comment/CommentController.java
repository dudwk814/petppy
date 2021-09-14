package petppy.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petppy.domain.Comment;
import petppy.dto.CommentDTO;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.service.comment.CommentService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment/")
public class CommentController {

    private final CommentService commentService;

    /**
     * comment list 조회
     */
    @GetMapping(value = "/{boardId}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResultDTO<CommentDTO, Comment>> getList(@PathVariable("boardId") Long boardId, @PathVariable("page") int page) {

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(page);

        PageResultDTO<CommentDTO, Comment> result = commentService.findCommentByBoardIdWithPaging(boardId, requestDTO);

        return new ResponseEntity<>(result, OK);
    }

    /**
     * comment 등록
     */
    @PostMapping("/{boardId}")
    public ResponseEntity<Long> addComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO savedComment = commentService.createComment(commentDTO);

        return new ResponseEntity<>(savedComment.getId(), OK);

    }

    /**
     * comment 삭제
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@RequestBody CommentDTO commentDTO) {
        commentService.deleteComment(commentDTO);

        return new ResponseEntity<>("success", OK);
    }

    /**
     * comment 수정
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<String> modifyComment(@RequestBody CommentDTO commentDTO) {
        commentService.modifyComment(commentDTO);
        return new ResponseEntity<>("success", OK);
    }
}
