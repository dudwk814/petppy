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

        emailService.sendEmail(emailDTO);
    }
}