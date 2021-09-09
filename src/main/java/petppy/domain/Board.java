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
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Lob
    private String content;

    private int commentCount;

    private int hit;

    @Builder
    public Board(Long id, User user, String title, String content) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.commentCount = 0;
        this.hit = 0;
    }

    public void changeBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeHitCount() {
        this.hit += 1;
    }

    public void plusCommentCount() {
        this.commentCount += 1;
    }

    public void minusCommentCount() {
        this.commentCount -= 1;
    }
}
