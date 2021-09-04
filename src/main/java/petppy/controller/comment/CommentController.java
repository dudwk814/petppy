package petppy.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.dto.CommentDTO;
import petppy.service.comment.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment/")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentDTO>> getList(@PathVariable("boardId") Long boardId) {

        List<CommentDTO> result = commentService.findCommentsByBoardId(boardId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
