package petppy.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import petppy.dto.user.MembershipCountDTO;
import petppy.repository.user.MembershipRepository;
import petppy.service.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final MembershipRepository membershipRepository;
    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public String adminPage(Model model) {

        List<MembershipCountDTO> result = membershipRepository.countByMembershipWithRating();
        int userCount = 0;

        for (MembershipCountDTO membershipCountDTO : result) {
            userCount += membershipCountDTO.getCount();
            model.addAttribute(membershipCountDTO.getRating().toString(), membershipCountDTO);  // key : NONE, PERSONAL ... value : membershipCountDTO
        }


        model.addAttribute("userCount", userCount);



        return "admin/adminPage";
    }
}
