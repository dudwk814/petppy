package petppy.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;
import petppy.domain.user.Rating;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.enquiry.EnquiryCountDTO;
import petppy.dto.reserve.ReserveCountDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.dto.user.MembershipCountDTO;
import petppy.dto.user.UserDTO;
import petppy.repository.reserve.ReserveRepository;
import petppy.repository.user.MembershipRepository;
import petppy.service.enquiry.EnquiryService;
import petppy.service.reserve.ReserveService;
import petppy.service.user.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final MembershipRepository membershipRepository;
    private final UserService userService;
    private final ReserveRepository reserveRepository;
    private final ReserveService reserveService;
    private final EnquiryService enquiryService;

    @GetMapping("")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public String adminPage(Model model) {

        int userCount = 0;
        int reserveCount = 0;
        int enquiryCount = 0;

        List<MembershipCountDTO> membershipCountDTOS = membershipRepository.countByMembershipWithRating();

        for (MembershipCountDTO membershipCountDTO : membershipCountDTOS) {
            userCount += membershipCountDTO.getCount();
            model.addAttribute(membershipCountDTO.getRating().toString(), membershipCountDTO);  // key : NONE, PERSONAL ... value : membershipCountDTO
        }

        List<ReserveCountDTO> reserveCountDTOS = reserveRepository.countByServicesType(ReserveType.RESERVE);

        for (ReserveCountDTO reserveCountDTO : reserveCountDTOS) {
            reserveCount += reserveCountDTO.getCount();
            model.addAttribute(reserveCountDTO.getServicesType().toString(), reserveCountDTO);  // key: DOG_WALK, PET_GROOMING, VET_VISIT
        }

        List<EnquiryCountDTO> enquiryCountDTOS = enquiryService.countByEnquiryWithEnquiryType(EnquiryStatus.BEFORE);
        for (EnquiryCountDTO enquiryCountDTO : enquiryCountDTOS) {
            enquiryCount += enquiryCountDTO.getCount();
            model.addAttribute(enquiryCountDTO.getEnquiryType().toString(), enquiryCountDTO);
        }



        model.addAttribute("userCount", userCount);
        model.addAttribute("reserveCount", reserveCount);
        model.addAttribute("enquiryCount", enquiryCount);

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

    @GetMapping("/reserve/{page}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<PageResultDTO<ReserveDTO, Reserve>> reserveList(@PathVariable("page") int page, String keyword, ReserveType reserveType, ServicesType servicesType) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        pageRequestDTO.setPage(page);
        pageRequestDTO.setKeyword(keyword);

        ReserveDTO reserveDTO = ReserveDTO.builder().reserveType(reserveType).servicesType(servicesType).build();


        return new ResponseEntity<>(reserveService.searchReserve(reserveDTO, pageRequestDTO), HttpStatus.OK);
    }
}
