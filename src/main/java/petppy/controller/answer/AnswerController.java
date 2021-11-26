package petppy.controller.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.dto.answer.AnswerDTO;
import petppy.service.answer.AnswerService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer/")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<String> createAnswer(@RequestBody @Valid AnswerDTO answerDTO, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>("답변 내용을 입력해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        answerService.createAnswer(answerDTO);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
