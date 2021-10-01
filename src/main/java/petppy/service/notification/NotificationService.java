package petppy.service.notification;

import org.springframework.data.domain.Pageable;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;
import petppy.domain.notificaton.NotificationType;
import petppy.domain.user.User;
import petppy.dto.notification.NotificationDTO;

import java.util.List;

import static petppy.domain.notificaton.NotificationType.*;

public interface NotificationService {

    public void createNotification(NotificationDTO dto);

    public NotificationDTO findNotification(Long id);

    public List<NotificationDTO> findNotificationList(String targetEmail);

    public List<NotificationDTO> findRecentNotificationList(String targetEmail, Pageable pageable);

    public Long unCheckedCount(String targetEmail);

    default Notification dtoToEntity(NotificationDTO dto) {

        if (dto.getNotificationType().equals("comment")) {
            return Notification
                    .builder()
                    .notificationType(COMMENT)
                    .targetEmail(dto.getTargetEmail())
                    .message(dto.getMessage())
                    .isRead(IsRead.N)
                    .build();
        } else if (dto.getNotificationType().equals("reserve")) {
            return Notification
                    .builder()
                    .notificationType(RESERVE)
                    .targetEmail(dto.getTargetEmail())
                    .message(dto.getMessage())
                    .isRead(IsRead.N)
                    .build();
        } else {
            return Notification
                    .builder()
                    .notificationType(NOTICE)
                    .targetEmail(dto.getTargetEmail())
                    .message(dto.getMessage())
                    .isRead(IsRead.N)
                    .build();
        }

    }

    default NotificationDTO entityToDto(Notification notification) {

        if (notification.getNotificationType() == COMMENT) {
            return NotificationDTO
                    .builder()
                    .id(notification.getId())
                    .createdDate(notification.getCreatedDate())
                    .message(notification.getMessage())
                    .isRead(notification.getIsRead())
                    .targetEmail(notification.getTargetEmail())
                    .notificationType("댓글")
                    .build();
        } else if (notification.getNotificationType() == RESERVE) {
            return NotificationDTO
                    .builder()
                    .id(notification.getId())
                    .createdDate(notification.getCreatedDate())
                    .message(notification.getMessage())
                    .isRead(notification.getIsRead())
                    .targetEmail(notification.getTargetEmail())
                    .notificationType("예약")
                    .build();
        } else {
            return NotificationDTO
                    .builder()
                    .id(notification.getId())
                    .createdDate(notification.getCreatedDate())
                    .message(notification.getMessage())
                    .isRead(notification.getIsRead())
                    .targetEmail(notification.getTargetEmail())
                    .notificationType("공지")
                    .build();
        }

    }
}
