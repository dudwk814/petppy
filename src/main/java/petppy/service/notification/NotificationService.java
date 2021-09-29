package petppy.service.notification;

import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;
import petppy.domain.user.User;
import petppy.dto.notification.NotificationDTO;

import java.util.List;

public interface NotificationService {

    public void createNotification(NotificationDTO dto);

    public NotificationDTO findNotification(Long id);

    public List<NotificationDTO> findNotificationList(String targetEmail);

    default Notification dtoToEntity(NotificationDTO dto) {
        return Notification
                .builder()
                .notificationType(dto.getNotificationType())
                .targetEmail(dto.getTargetEmail())
                .message(dto.getMessage())
                .isRead(IsRead.N)
                .build();
    }

    default NotificationDTO entityToDto(Notification notification) {
        return NotificationDTO
                .builder()
                .id(notification.getId())
                .createdDate(notification.getCreatedDate())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .targetEmail(notification.getTargetEmail())
                .build();
    }
}
