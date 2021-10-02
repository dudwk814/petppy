package petppy.controller.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petppy.domain.notificaton.Notification;
import petppy.domain.notificaton.NotificationType;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
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

    @GetMapping("/{email}/{page}")
    public ResponseEntity<PageResultDTO<NotificationDTO, Notification>> findNotifyByEmail(@PathVariable("email") String email, @PathVariable("page") int page ) {

        PageRequestDTO requestDTO = new PageRequestDTO();
        requestDTO.setPage(page);

        PageResultDTO<NotificationDTO, Notification> result = notificationService.findNotificationList(email, requestDTO);

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

    @PatchMapping("/{id}")
    public ResponseEntity<String> readItNotification(@PathVariable("id") Long id) {

        notificationService.readItNotification(id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PatchMapping("/allReadCheck/{email}")
    public ResponseEntity<String> allReadCheck(@PathVariable("email") String email) {

        notificationService.allReadCheck(email);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
