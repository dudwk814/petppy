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
     * 회원 주소 설정
     */
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/modifyUserAddress")
    public ResponseEntity<Boolean> modifyUserAddress(@RequestBody UserDTO userDTO) {

        System.out.println("userDTO = " + userDTO);

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


}
