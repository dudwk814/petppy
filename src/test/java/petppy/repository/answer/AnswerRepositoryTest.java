package petppy.repository.answer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.answer.Answer;
import petppy.domain.enquiry.Enquiry;
import petppy.exception.EnquiryNotFoundException;
import petppy.repository.enquiry.EnquiryRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerRepositoryTest {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    EnquiryRepository enquiryRepository;

    @Test
    @Commit
    public void 문의_답변_생성() throws Exception {
        Answer answer = Answer.builder()
                .content("test answer")
                .enquiry(enquiryRepository.findById(101L).orElseThrow(EnquiryNotFoundException::new)).build();

        answerRepository.save(answer);


    }

}