package petppy.controller.enquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import petppy.dto.enquiry.EnquiryDTO;
import petppy.service.enquiry.EnquiryService;

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
            return new ResponseEntity<>("문의 내용을 입력해주세요!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        enquiryService.createEnquiry(enquiryDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
