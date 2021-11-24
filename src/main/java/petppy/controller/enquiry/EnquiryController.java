package petppy.controller.enquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.user.Role;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.enquiry.EnquiryDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.dto.user.UserDTO;
import petppy.service.enquiry.EnquiryService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/enquiry")
@RequiredArgsConstructor
public class EnquiryController {

    private final EnquiryService enquiryService;

    /**
     * 문의 생성
     * @param enquiryDTO
     * @param result
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody @Valid EnquiryDTO enquiryDTO, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>("문의 제목 혹은 내용을 입력해주세요!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        enquiryService.createEnquiry(enquiryDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{enquiryId}")
    public ResponseEntity<EnquiryDTO> findEnquiry(@PathVariable("enquiryId") Long enquiryId, String email, HttpSession session) {

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        String userEmail = userDTO.getEmail();
        Role userRole = userDTO.getRole();

        if (email.equals(userEmail) || userRole == Role.ADMIN) {
            return new ResponseEntity<>(enquiryService.findEnquiry(enquiryId), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/enquiryList")
    public String enquiryList(HttpSession session, Model model, EnquiryDTO enquiryDTO, PageRequestDTO requestDTO) {

        String userEmail = (String) session.getAttribute("userEmail");
        enquiryDTO.setUserEmail(userEmail);

        model.addAttribute("enquiryList", enquiryService.findEnquiryListWithPaging(enquiryDTO, requestDTO));

        return "enquiry/enquiryList";
    }

    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(HttpSession session, EnquiryDTO enquiryDTO) {

        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail.equals(enquiryDTO.getUserEmail()) != true) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        enquiryService.deleteEnquiry(enquiryDTO.getEnquiryId());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
