package petppy.repository.enquiry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.domain.enquiry.EnquiryType;
import petppy.domain.user.User;
import petppy.dto.enquiry.EnquiryCountDTO;
import petppy.exception.EnquiryNotFoundException;
import petppy.exception.UserNotFoundException;
import petppy.repository.user.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EnquiryRepositoryTest {

    @Autowired
    EnquiryRepository enquiryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Commit
    public void 문의_생성() throws Exception {

        for (int i = 0; i < 435; i++) {
            User user = userRepository.findById(1L).orElseThrow(UserNotFoundException::new);

            Enquiry enquiry = Enquiry.builder().user(user).enquiryType(EnquiryType.MEMBERSHIP).content("TEST Enquiry" + i).title("TEST Enquiry" + i).build();

            enquiryRepository.save(enquiry);
        }

    }

    @Test
    public void 문의_조회() throws Exception {
        Enquiry enquiry = enquiryRepository.findById(1L).orElseThrow(EnquiryNotFoundException::new);
        System.out.println("enquiry = " + enquiry);
    }

    @Test
    public void 이메일로_문의_리스트_조회() throws Exception {
        List<Enquiry> result = enquiryRepository.findByUserEmail("kj99658103@gmail.com");

        assertEquals(1, result.size());
    }

    @Test
    @Commit
    public void 문의_수정() throws Exception {
        Enquiry enquiry = enquiryRepository.findById(1L).orElseThrow(EnquiryNotFoundException::new);

        enquiry.changeContent("TEST CHANGE");
    }

    @Test
    public void 문의_종류_별_카운트() throws Exception {

        List<EnquiryCountDTO> result = enquiryRepository.countByEnquiryWithEnquiryType(EnquiryStatus.BEFORE);

        for (EnquiryCountDTO enquiryCountDTO : result) {
            System.out.println("enquiryCountDTO = " + enquiryCountDTO);
        }
    }
}