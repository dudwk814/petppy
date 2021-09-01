package petppy.domain;

import lombok.*;
import petppy.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString(exclude = {"user"})
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    private String title;

    private String content;

    private int replyCount;

    private int hit;

    @Builder
    public Board(Long id, User user, String title, String content) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.replyCount = 0;
        this.hit = 0;
    }

    public void changeBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeHitCount() {
        this.hit += 1;
    }

    public void changeReplyCount() {
        this.replyCount += 1;
    }
}
