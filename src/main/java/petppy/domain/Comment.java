package petppy.domain;

import lombok.*;
import petppy.domain.user.User;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static petppy.domain.DeleteStatus.N;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString(exclude = {"board", "user"})
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private DeleteStatus isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @Builder
    public Comment(Board board, User user, String content, Comment parent) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.parent = parent;
        this.isDeleted = N;
    }

    public void changeComment(String content) {
        this.content = content;
    }

    public void changeDeletedStatus(DeleteStatus deleteStatus) {
        this.isDeleted = deleteStatus;
    }
}
