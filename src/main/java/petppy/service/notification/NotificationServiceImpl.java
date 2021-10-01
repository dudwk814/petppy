package petppy.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;
import petppy.dto.notification.NotificationDTO;
import petppy.repository.notification.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createNotification(NotificationDTO dto) {

        Notification notification = dtoToEntity(dto);

        notificationRepository.save(notification);

    }

    @Override
    public NotificationDTO findNotification(Long id) {

        Notification notification = notificationRepository.findById(id).get();

        return entityToDto(notification);
    }

    @Override
    public List<NotificationDTO> findNotificationList(String targetEmail) {
        return notificationRepository.findByTargetEmail(targetEmail)
                .stream()
                .map(notification -> entityToDto(notification))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> findRecentNotificationList(String targetEmail, Pageable pageable) {

        return notificationRepository.findRecentByTargetEmail(targetEmail, pageable)
                .stream()
                .map(notification -> entityToDto(notification))
                .collect(Collectors.toList());
    }

    @Override
    public Long unCheckedCount(String targetEmail) {

        IsRead isRead = IsRead.N;
        return notificationRepository.unCheckedNotification(targetEmail, isRead);
    }
}
