package petppy.repository.notification;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationRepositoryCustom {

    public List<Notification> findByTargetEmail(String targetEmail);

    @Query("select n from Notification n where n.targetEmail = :targetEmail order by n.createdDate desc")
    public List<Notification> findRecentByTargetEmail(@Param("targetEmail") String targetEmail, Pageable pageable);

    @Query("select count (n) from Notification n where n.isRead = :isRead and n.targetEmail = :targetEmail")
    public Long unCheckedNotification(@Param("targetEmail") String targetEmail, @Param("isRead") IsRead isRead);

    @Modifying
    @Query("update Notification n set n.isRead = :isRead where n.targetEmail = :targetEmail")
    public void allReadCheck(@Param("targetEmail") String targetEmail, @Param("isRead") IsRead isRead);

    void deleteByTargetEmail(String email);
}
