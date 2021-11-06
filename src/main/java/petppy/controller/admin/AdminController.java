package petppy.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import petppy.domain.reserve.ReserveType;
import petppy.domain.user.Rating;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.reserve.ReserveCountDTO;
import petppy.dto.user.MembershipCountDTO;
import petppy.dto.user.UserDTO;
import petppy.repository.reserve.ReserveRepository;
import petppy.repository.user.MembershipRepository;
import petppy.service.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final MembershipRepository membershipRepository;
    private final UserService userService;
    private final ReserveRepository reserveRepository;

    @GetMapping("")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public String adminPage(Model model) {

        List<MembershipCountDTO> membershipCountDTOS = membershipRepository.countByMembershipWithRating();
        int userCount = 0;
        int reserveCount = 0;

        for (MembershipCountDTO membershipCountDTO : membershipCountDTOS) {
            userCount += membershipCountDTO.getCount();
            model.addAttribute(membershipCountDTO.getRating().toString(), membershipCountDTO);  // key : NONE, PERSONAL ... value : membershipCountDTO
        }

        List<ReserveCountDTO> reserveCountDTOS = reserveRepository.countByServicesType(ReserveType.RESERVE);

        for (ReserveCountDTO reserveCountDTO : reserveCountDTOS) {
            reserveCount += reserveCountDTO.getCount();
            model.addAttribute(reserveCountDTO.getServicesType().toString(), reserveCountDTO);  // key: DOG_WALK, PET_GROOMING, VET_VISIT
        }


        model.addAttribute("userCount", userCount);
        model.addAttribute("reserveCount", reserveCount);

        return "admin/adminPage";
    }

    @GetMapping("/user/{page}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<PageResultDTO<UserDTO, User>> userList(@PathVariable("page") int page, String keyword, Rating rating) {

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(page);
        requestDTO.setKeyword(keyword);
        requestDTO.setRating(rating);

        return new ResponseEntity<>(userService.searchUser(requestDTO), HttpStatus.OK);
    }
}
