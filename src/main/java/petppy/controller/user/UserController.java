package petppy.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import petppy.dto.reserve.ReserveDTO;
import petppy.dto.user.MembershipDTO;
import petppy.dto.user.UserDTO;
import petppy.service.board.BoardService;
import petppy.service.comment.CommentService;
import petppy.service.notification.NotificationService;
import petppy.service.reserve.ReserveService;
import petppy.service.user.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static petppy.domain.user.Type.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final NotificationService notificationService;
    private final FormValidator formValidator;
    private final ReserveService reserveService;

    /**
     * 회원 설정 페이지
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public String userPage(HttpSession session, Model model) {

        String userEmail = (String)session.getAttribute("userEmail");
        Type type = (Type) session.getAttribute("type");


        model.addAttribute("userDTO", userService.findByEmailAndType(userEmail, type));

        return "user/userPage";
    }

    /**
     * 회원 DashBoard
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session,  Model model) {

        String userEmail = (String)session.getAttribute("userEmail");

        Type type = (Type) session.getAttribute("type");

        model.addAttribute("userDTO", userService.findByEmailAndType(userEmail, type));
        model.addAttribute("unCheckedNotifyCount", notificationService.unCheckedCount(userEmail));

        return "user/dashboard";
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
    public String signup(@Valid UserDTO userDTO, BindingResult result,  Model model, RedirectAttributes rttr) {

        if (result.hasErrors()) {

            Map<String, String> validatorResult = formValidator.validateHandling(result);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "signup";
        }
        userDTO.setType(NORMAL);

        String name = userService.joinedMember(userDTO);

        rttr.addFlashAttribute("success", name);

        return "redirect:/";

    }

    /**
     * 회원 정보 변경 form으로 이동
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modifyForm")
    public String modifyForm(HttpSession session, Model model) {

        UserDTO userDTO = (UserDTO)session.getAttribute("user");

        String userEmail = userDTO.getEmail();

        UserDTO findUserDTO = userService.findByEmail(userEmail);

        model.addAttribute("address", findUserDTO);

        return "user/modifyForm";
    }

    /**
     * 회원 탈퇴
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/disabled")
    public String disabled(UserDTO userDTO, HttpSession session) {

        userService.disabled(userDTO);

        session.removeAttribute("user");
        session.removeAttribute("userEmail");
        session.removeAttribute("type");

        return "redirect:/";
    }

    /**
     * 회원 주소 설정
     */
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/modifyUserAddress")
    public ResponseEntity<Boolean> modifyUserAddress(@RequestBody UserDTO userDTO) {

        log.info("userDTO = " + userDTO);

        return new ResponseEntity<>(userService.ModifyUserAddress(userDTO), HttpStatus.OK);
    }

    /**
     * 회원 이메일 중복 체크
     * @param email
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/checkEmailExist/{email}")
    public ResponseEntity<Boolean> checkEmailExist(@PathVariable("email") String email) {
        return new ResponseEntity<>(userService.checkEmailExist(email), HttpStatus.OK);
    }

    /**
     * ajax로 회원 조회
     * @param email
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @GetMapping(value = "/{email}")
    public ResponseEntity<UserDTO> findUserWithAjax(@PathVariable("email") String email, HttpSession session) {

        String userEmail = (String)session.getAttribute("userEmail");
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (userEmail.equals(email) || user.getRole().getKey().equals("ROLE_ADMIN")) {  // 세션에서 조회한 이메일(로그인시 이메일)과 ajax로 넘어온 이메일이 같아야함
            return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }


    /**
     * 멤버십 등급 조회
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/membership/{userId}")
    public ResponseEntity<MembershipDTO> findMembership(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(userService.findMembership(userId), HttpStatus.OK);
    }


    @ResponseBody
    @PatchMapping(value = "/{email}")
    public ResponseEntity<Boolean> changePassword(@PathVariable("email") String email, @RequestBody UserDTO userDTO) {

        if (userService.changePassword(userDTO)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/findPassword")
    public String findPasswordForm() {
        return "user/findPassword";
    }


}
