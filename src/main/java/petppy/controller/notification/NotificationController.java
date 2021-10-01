package petppy.controller.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petppy.domain.notificaton.NotificationType;
import petppy.dto.notification.NotificationDTO;
import petppy.service.notification.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notify/")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/save")
    public ResponseEntity<String> createNotification(@RequestBody NotificationDTO dto) {

        notificationService.createNotification(dto);


        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<NotificationDTO>> findNotifyByEmail(@PathVariable("email") String email) {

        List<NotificationDTO> result = notificationService.findNotificationList(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/unChecked/{email}")
    public ResponseEntity<Long> unCheckedCount(@PathVariable("email") String email) {

        Long result = notificationService.unCheckedCount(email);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/recent/{email}")
    public ResponseEntity<List<NotificationDTO>> findRecentNotifyByEmail(@PathVariable("email") String email) {

        PageRequest pageable = PageRequest.of(0, 5);
        List<NotificationDTO> result = notificationService.findRecentNotificationList(email, pageable);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
