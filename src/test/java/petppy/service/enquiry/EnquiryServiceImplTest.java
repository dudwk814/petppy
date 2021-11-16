package petppy.service.enquiry;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.enquiry.EnquiryType;
import petppy.dto.enquiry.EnquiryDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EnquiryServiceImplTest {

    @Autowired
    EnquiryService enquiryService;

    @Test
    @Commit
    public void 문의_생성() throws Exception {
        EnquiryDTO enquiryDTO = EnquiryDTO.builder().enquiryType(EnquiryType.ETC).content("test2").userId(1L).build();

        enquiryService.createEnquiry(enquiryDTO);
    }

    @Test
    @Commit
    public void 문의_수정() throws Exception {
        EnquiryDTO enquiryDTO = EnquiryDTO.builder().enquiryId(2L).enquiryType(EnquiryType.ETC).content("test modify").userId(1L).build();
        enquiryService.changeEnquiry(enquiryDTO);
    }

    @Test
    public void 문의_조회() throws Exception {
        EnquiryDTO enquiry = enquiryService.findEnquiry(2L);
        System.out.println("enquiry = " + enquiry);
    }

    @Test
    public void 이메일로_문의_리스트_조회() throws Exception {
        List<EnquiryDTO> result = enquiryService.findEnquiryListWithUserEmail("kj99658103@gmail.com");

        result.stream().forEach(enquiryDTO -> System.out.println("enquiryDTO = " + enquiryDTO));
    }

    @Test
    @Commit
    public void 문의_삭제() throws Exception {
        enquiryService.deleteEnquiry(2L);
    }

}