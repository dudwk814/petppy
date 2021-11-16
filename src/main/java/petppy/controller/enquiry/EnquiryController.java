package petppy.controller.enquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import petppy.dto.enquiry.EnquiryDTO;
import petppy.service.enquiry.EnquiryService;

@Controller
@RequestMapping("/enquiry")
@RequiredArgsConstructor
public class EnquiryController {

    private final EnquiryService enquiryService;

    @ResponseBody
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody EnquiryDTO enquiryDTO) {

        enquiryService.createEnquiry(enquiryDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
