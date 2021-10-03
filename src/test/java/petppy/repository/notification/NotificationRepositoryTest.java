package petppy.repository.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;
import petppy.domain.notificaton.NotificationType;

import static org.junit.jupiter.api.Assertions.*;
import static petppy.domain.notificaton.NotificationType.COMMENT;

@SpringBootTest
@Transactional
class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;

    @Commit
    @Test
    public void 알림_생성_100개() throws Exception {


        for (int i = 0; i < 100; i++) {
            Notification notification = Notification
                    .builder()
                    .notificationType(COMMENT)
                    .url("/board/read?id=3")
                    .targetEmail("kj99658103@gmail.com")
                    .message("3번 글에 댓글이 작성되었습니다.")
                    .isRead(IsRead.N)
                    .build();

            notificationRepository.save(notification);
        }
    }

}