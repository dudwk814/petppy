package petppy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import petppy.domain.Board;
import petppy.dto.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.UserDTO;
import petppy.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

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
