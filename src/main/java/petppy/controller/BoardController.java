package petppy.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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

    /**
     * CKEDITOR 이미지 업로드
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER')")
    @PostMapping("/uploadImg")
    public void postCKEditorImgUpload(HttpServletRequest req,
                                      HttpServletResponse res,
                                      @RequestParam MultipartFile upload) throws Exception {

        // 랜덤 문자 생성
        UUID uuid = UUID.randomUUID();

        OutputStream out = null;
        PrintWriter printWriter = null;

        // 인코딩
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html;charset=utf-8");

        try {

            String fileName = upload.getOriginalFilename();  // 파일 이름 가져오기
            byte[] bytes = upload.getBytes();

            // 업로드 경로
            String defaultPath = req.getSession().getServletContext().getRealPath("/");


            String ckUploadPath = "C:\\upload\\petppy\\ckUpload" + File.separator + uuid + "_" + fileName;


            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush();  // out에 저장된 데이터를 전송하고 초기화

            String callback = req.getParameter("CKEditorFuncNum");
            printWriter = res.getWriter();
            String fileUrl = "C:\\upload\\petppy\\ckUpload" + File.separator + uuid + "_" + fileName;  // 작성화면



            // 업로드시 메시지 출력
            printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");

            printWriter.flush();

        } catch (IOException e) { e.printStackTrace();
        } finally {
            try {
                if(out != null) { out.close(); }
                if(printWriter != null) { printWriter.close(); }
            } catch(IOException e) { e.printStackTrace(); }
        }

        return;
    }

}
