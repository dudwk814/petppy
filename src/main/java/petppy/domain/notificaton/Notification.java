package petppy.domain.notificaton;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petppy.domain.BaseTimeEntity;
import petppy.domain.user.User;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static petppy.domain.notificaton.IsRead.Y;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Long id;

    @Column(name = "target_email")
    private String targetEmail;

    @Column(name = "not_message")
    private String message;

    @Enumerated(value = EnumType.STRING)
    private IsRead isRead;

    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;

    public void readNotify() {
        this.isRead = Y;
    }


}
