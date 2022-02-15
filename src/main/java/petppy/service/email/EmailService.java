package petppy.service.email;

import petppy.domain.email.Email;
import petppy.dto.email.EmailDTO;

public interface EmailService {

    public EmailDTO sendEmail(EmailDTO emailDTO);

    public boolean checkAuthCode(EmailDTO emailDTO);

    default EmailDTO entityToDTO(Email email) {
        return EmailDTO
                .builder()
                .id(email.getId())
                .email(email.getEmail())
                .authCode(email.getAuthCode())
                .message(email.getMessage())
                .title(email.getTitle())
                .build();
    }

    default Email dtoToEntity(EmailDTO emailDTO) {
        return Email
                .builder()
                .email(emailDTO.getEmail())
                .authCode(emailDTO.getAuthCode())
                .message(emailDTO.getMessage())
                .title(emailDTO.getTitle())
                .build();
    }
}
