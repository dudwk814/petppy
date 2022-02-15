package petppy.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.email.Email;
import petppy.dto.email.EmailDTO;
import petppy.exception.EmailNotFoundException;
import petppy.repository.email.EmailRepository;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
//@PropertySource(value = "classpath:/application-email.yml", ignoreResourceNotFound = true)
@PropertySource(value = "/home/ec2-user/app/application-email.yml", ignoreResourceNotFound = true)
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    /**
     * 이메일로 발송한 인증코드와 DB에 저장된 인증코드 비교
     * @param emailDTO
     * @return
     */
    @Override
    public boolean checkAuthCode(EmailDTO emailDTO) {

        Email email = emailRepository.findById(emailDTO.getId()).orElseThrow(EmailNotFoundException::new);

        if (email.getAuthCode().equals(emailDTO.getAuthCode())) {   // view화면에서 입력한 인증코드와 조회한 email의 인증코드가 같다면 true
            return true;
        }

        return false;
    }

    /**
     * 이메일 발송
     * @param emailDTO
     * @return
     */
    @Override
    public EmailDTO sendEmail(EmailDTO emailDTO) {

        emailDTO.setAuthCode(makeAuthCode());

        emailDTO.setTitle("Petppy 비밀번호 변경 인증코드");
        emailDTO.setMessage("Petppy 비밀번호 인증코드는 " + emailDTO.getAuthCode() + "입니다.");


        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailDTO.getEmail());
        message.setFrom(fromEmail);
        message.setSubject(emailDTO.getTitle());
        message.setText(emailDTO.getMessage());

        mailSender.send(message);

        return saveEmail(dtoToEntity(emailDTO));
    }

    /**
     * 이메일 정보 DB에 저장
     * @param email
     */
    @Transactional
    protected EmailDTO saveEmail(Email email) {
        return entityToDTO(emailRepository.save(email));
    }

    /**
     * 인증코드 생성
     * @return
     */
    private String makeAuthCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) { // 인증코드 6자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }
}
