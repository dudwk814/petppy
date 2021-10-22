package petppy.service.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.dto.email.EmailDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EmailServiceImplTest {

    @Autowired
    EmailService emailService;

    @Test
    @Commit
    public void 이메일_생성() throws Exception {
        EmailDTO emailDTO = EmailDTO.builder()
                .email("kj99658103@gmail.com")
                .build();

        EmailDTO email = emailService.sendEmail(emailDTO);

        System.out.println("email.getId() = " + email.getId());
    }

    @Test
    public void 인증코드_비교() throws Exception {
        EmailDTO emailDTO = EmailDTO.builder()
                .id(35L)
                .authCode("282771")
                .build();

        boolean result = emailService.checkAuthCode(emailDTO);

        assertEquals(true, result);    // 해당 emailDTO id값으로 조회한 데이터의 auth_code값과 emailDTO를 생성할때 입력한 authCode값이 같아야 한다.
    }
}