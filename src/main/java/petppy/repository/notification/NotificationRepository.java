package petppy.repository.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.notificaton.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    public List<Notification> findByTargetEmail(String targetEmail);
}
