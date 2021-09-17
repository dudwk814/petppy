package petppy.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import petppy.domain.user.Type;
import petppy.dto.user.UserDTO;
import petppy.service.user.UserService;

import javax.servlet.http.HttpSession;

import static petppy.domain.user.Type.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    /**
     * 회원 정보 페이지
     */
    @PostMapping("")
    public String userPage(
            @Param("email") String email,
            @Param("type") String type,
            @Param("userId") Long id,
            Model model) {

        Type userType = NORMAL;

        /* 회원 가입 타입(소셜 회원 or 일반 회원에 따라  */
        if (type.equals("GOOGLE")) {
            userType = GOOGLE;
        } else if (type.equals("NAVER")) {
            userType = NAVER;
        } else if (type.equals("KAKAO")) {
            userType = KAKAO;
        }

        UserDTO result = userService.findByEmailAndType(email, userType);

        model.addAttribute("userDTO", result);

        return "/user/userPage";
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

        rttr.addAttribute("msg", name);

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
