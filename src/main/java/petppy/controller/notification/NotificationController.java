package petppy.controller.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petppy.domain.notificaton.NotificationType;
import petppy.dto.notification.NotificationDTO;
import petppy.service.notification.NotificationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("save")
    public ResponseEntity<String> createNotification(@RequestBody NotificationDTO dto) {

        dto.setNotificationType(NotificationType.COMMENT);

        notificationService.createNotification(dto);


        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
