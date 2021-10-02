package petppy.service.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.board.Board;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.board.BoardDto;
import petppy.dto.notification.NotificationDTO;
import petppy.exception.NotificationNotFoundException;
import petppy.repository.notification.NotificationRepository;

import java.util.List;
import java.util.function.Function;
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
    public PageResultDTO<NotificationDTO, Notification> findNotificationList(String targetEmail, PageRequestDTO requestDTO) {

        Page<Notification> result = notificationRepository.findNotificationList(targetEmail, requestDTO);

        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();



        Function<Notification, NotificationDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
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

    /**
     * Notification을 읽음 상태로 변경
     * @param id
     */
    @Transactional
    @Override
    public void readItNotification(Long id) {
        Notification result = notificationRepository.findById(id).orElseThrow(NotificationNotFoundException::new);

        result.readNotify();
    }

    @Override
    public void allReadCheck(String email) {
        notificationRepository.allReadCheck(email, IsRead.Y);
    }
}
