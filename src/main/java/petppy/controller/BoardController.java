package petppy.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import petppy.domain.Board;
import petppy.dto.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.UserDTO;
import petppy.service.BoardService;
import petppy.service.comment.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("")
    public String boardList(Model model, PageRequestDTO requestDTO, String boardSort) {

        PageResultDTO<BoardDto, Board> result = boardService.searchBoardList(requestDTO);

        model.addAttribute("boardList", result);

        model.addAttribute("boardSort", boardSort);

        return "/board/boardList";
    }

    @GetMapping("/read")
    public String read(Model model, Long id) {

        BoardDto boardDto = boardService.searchBoard(id);

        model.addAttribute("board", boardDto);

        model.addAttribute("commentList", commentService.findCommentsByBoardId(id));

        model.addAttribute("recentBoardList", boardService.findRecentBoardList(PageRequest.of(0, 5)));

        return "/board/read";
    }

    @GetMapping("/edit")
    public String editForm() {

        return "/board/edit";
    }

    @PostMapping("/edit")
    public String edit(BoardDto boardDto, HttpSession session) {

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        boardDto.setUserId(userDTO.getId());

        System.out.println("boardDto = " + boardDto);

        boardService.createBoard(boardDto);

        return "redirect:/board";
    }

}
