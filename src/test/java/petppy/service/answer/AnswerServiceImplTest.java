package petppy.service.answer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.dto.answer.AnswerDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerServiceImplTest {

    @Autowired
    AnswerService answerService;

    @Test
    @Commit
    public void 문의_답변_생성() throws Exception {

        AnswerDTO answerDTO = AnswerDTO.builder().enquiryId(101L).content("testAnswer").build();

        answerService.createAnswer(answerDTO);
    }
}