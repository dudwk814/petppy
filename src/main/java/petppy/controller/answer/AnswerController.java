package petppy.controller.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.dto.answer.AnswerDTO;
import petppy.service.answer.AnswerService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer/")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<String> createAnswer(@RequestBody AnswerDTO answerDTO) {
        answerService.createAnswer(answerDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
