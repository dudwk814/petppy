package petppy.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import petppy.config.util.FormValidator;
import petppy.domain.user.Type;
import petppy.dto.user.UserDTO;
import petppy.service.board.BoardService;
import petppy.service.comment.CommentService;
import petppy.service.notification.NotificationService;
import petppy.service.user.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Map;

import static petppy.domain.user.Type.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final NotificationService notificationService;
    private final FormValidator formValidator;

    /**
     * 회원 설정 페이지
     */
    @PreAuthorize("isAuthenticated()")
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
    public String signupForm(UserDTO userDTO, Model model) {

        return "signup";
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public String signup(@Valid UserDTO userDTO, BindingResult result,  Model model) {

        if (result.hasErrors()) {

            Map<String, String> validatorResult = formValidator.validateHandling(result);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "signup";
        }
        userDTO.setType(NORMAL);


        /*if (userService.checkMemberIdExist(userDTO) == true) {

            rttr.addFlashAttribute("checkEmail", true);

            return "redirect:/user/signupForm";
        }*/

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

    /**
     * 회원가입 이메일 중복 검증
     */
    @ResponseBody
    @GetMapping(value = "/checkEmailExist/{email}")
    public ResponseEntity<Boolean> checkEmailExist(@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.checkEmailExist(email), HttpStatus.OK);
    }
}
