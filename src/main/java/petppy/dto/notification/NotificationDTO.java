package petppy.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.NotificationType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    private Long id;

    private String targetEmail;

    private String message;

    private String url;

    private LocalDateTime createdDate;

    private IsRead isRead;

    private String notificationType;
}
