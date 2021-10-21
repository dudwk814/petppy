package petppy.controller.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.dto.email.EmailDTO;
import petppy.service.email.EmailService;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email/")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Long> sendEmail(@RequestBody EmailDTO emailDTO) {


        EmailDTO findEmailDTO = emailService.sendEmail(emailDTO);

        return new ResponseEntity<>(findEmailDTO.getId(), OK);
    }
}
