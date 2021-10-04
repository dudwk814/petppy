package petppy.controller.board;

import com.nhncorp.lucy.security.xss.XssPreventer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import petppy.domain.board.Board;
import petppy.dto.board.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.user.UserDTO;
import petppy.service.board.BoardService;
import petppy.service.comment.CommentService;
import petppy.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    @Value("${resources.location}")
    private String resourcesLocation;
    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

    @GetMapping("")
    public String boardList(Model model, @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {

        PageResultDTO<BoardDto, Board> result = boardService.searchBoardList(requestDTO);

        model.addAttribute("boardList", result);

        return "/board/boardList";
    }

    @GetMapping("/read")
    public String read(Model model, Long id, @ModelAttribute("requestDTO") PageRequestDTO requestDTO) {

        BoardDto boardDto = boardService.searchBoard(id);

        model.addAttribute("board", boardDto);

        /*model.addAttribute("commentList", commentService.findCommentsByBoardId(id));*/

        model.addAttribute("recentBoardList", boardService.findRecentBoardList(PageRequest.of(0, 5)));

        return "/board/read";
    }

    /**
     * 글 작성 폼으로 이동
     */
    @GetMapping("/edit")
    public String editForm() {

        return "/board/edit";
    }

    /**
     * 글 작성
     */
    @PostMapping("/edit")
    public String edit(BoardDto boardDto, HttpSession session) {

        String userEmail = (String) session.getAttribute("userEmail");

        UserDTO userDTO = userService.findByEmail(userEmail);

        boardDto.setUserId(userDTO.getId());

        boardDto.setContent(XssPreventer.unescape(boardDto.getContent()));

        System.out.println("boardDto = " + boardDto);

        boardService.createBoard(boardDto);

        return "redirect:/board";
    }

    /**
     * 글 수정 폼으로 이동
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', ROLE_MEMBER)")
    @GetMapping("/modify")
    public String modifyForm(Long id, PageRequestDTO requestDTO, Model model) {

        BoardDto boardDto = boardService.searchBoard(id);

        model.addAttribute("board", boardDto);

        return "/board/modify";
    }

    /**
     * 글 수정
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', ROLE_MEMBER)")
    @PostMapping("/modify")
    public String modify(BoardDto boardDto, PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {

        boardDto.setContent(XssPreventer.unescape(boardDto.getContent()));

        boardService.modifyBoard(boardDto);

        redirectAttributes.addFlashAttribute("requestDTO", requestDTO);

        return "redirect:/board";
    }

    /**
     * 글 삭제
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', ROLE_MEMBER)")
    @PostMapping("/delete")
    public String delete(Long id, PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        boardService.deleteBoard(id);

        redirectAttributes.addFlashAttribute("requestDTO", requestDTO);

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


            String ckUploadPath =  resourcesLocation + File.separator +  uuid + "_" + fileName; // linux

            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush();  // out에 저장된 데이터를 전송하고 초기화

            String callback = req.getParameter("CKEditorFuncNum");
            printWriter = res.getWriter();
            String fileUrl = resourcesLocation + "/" + uuid + "_" + fileName;  // 작성화면



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

    /**
     * 유저 이메일로 조회
     */
    @ResponseBody
    @GetMapping(value = "/{email}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<PageResultDTO<BoardDto, Board>> findUserEmail(@PathVariable("email") String email, @PathVariable("page") int page) {
        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(page);

        PageResultDTO<BoardDto, Board> result = boardService.findByUserEmail(requestDTO, email);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    /**
     * 게시글 댓글 갯수 조회
     */
    @ResponseBody
    @GetMapping(value = "/commentCount/{boardId}")
    public ResponseEntity<Long> commentCount(@PathVariable("boardId") Long boardId) {

        return new ResponseEntity<>(boardService.commentCount(boardId), HttpStatus.OK);
    }

}
