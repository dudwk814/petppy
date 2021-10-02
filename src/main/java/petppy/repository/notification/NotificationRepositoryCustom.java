package petppy.repository.notification;

import org.springframework.data.domain.Page;
import petppy.domain.notificaton.Notification;
import petppy.dto.PageRequestDTO;

public interface NotificationRepositoryCustom {

    Page<Notification> findNotificationList(String targetEmail, PageRequestDTO pageRequestDTO);
}
