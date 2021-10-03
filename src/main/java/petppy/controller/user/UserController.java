package petppy.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import petppy.domain.user.Type;
import petppy.dto.user.UserDTO;
import petppy.service.board.BoardService;
import petppy.service.comment.CommentService;
import petppy.service.notification.NotificationService;
import petppy.service.user.UserService;

import javax.servlet.http.HttpSession;

import static petppy.domain.user.Type.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final NotificationService notificationService;

    /**
     * 회원 설정 페이지
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', ROLE_MEMBER)")
    @GetMapping("")
    public String userPage(HttpSession session, Model model) {

        String userEmail = (String)session.getAttribute("userEmail");
        Type type = (Type) session.getAttribute("type");


        model.addAttribute("userDTO", userService.findByEmailAndType(userEmail, type));

        return "/user/userPage";
    }

    /**
     * 회원 DashBoard
     */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session,  Model model) {

        String userEmail = (String)session.getAttribute("userEmail");

        Type type = (Type) session.getAttribute("type");

        model.addAttribute("userDTO", userService.findByEmailAndType(userEmail, type));
        model.addAttribute("unCheckedNotifyCount", notificationService.unCheckedCount(userEmail));

        return "/user/dashboard";
    }

    /**
     * 회원가입 form으로 이동
     */
    @GetMapping("/signupForm")
    public String signupForm(Model model) {

        model.addAttribute("form", new UserDTO());
        return "signup";
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public String signup(UserDTO userDTO, RedirectAttributes rttr) {
        userDTO.setType(NORMAL);

        if (userService.checkMemberIdExist(userDTO) == true) {

            rttr.addFlashAttribute("checkEmail", true);

            return "redirect:/user/signupForm";
        }

        String name = userService.joinedMember(userDTO);

        return "redirect:/";

    }

    /**
     * 회원 정보 변경 form으로 이동
     */
    @GetMapping("/modifyForm")
    public String modifyForm(HttpSession session, Model model) {

        UserDTO userDTO = (UserDTO)session.getAttribute("user");

        String userEmail = userDTO.getEmail();

        UserDTO findUserDTO = userService.findByEmail(userEmail);

        model.addAttribute("address", findUserDTO);

        return "user/modifyForm";
    }

    /**
     * 회원 주소 설정
     */
    @PostMapping("/modifyUserAddress")
    public String modifyUserAddress(UserDTO userDTO) {

        System.out.println("userDTO = " + userDTO);

        userService.ModifyUserAddress(userDTO);

        return "redirect:/user/modifyForm";
    }
}
