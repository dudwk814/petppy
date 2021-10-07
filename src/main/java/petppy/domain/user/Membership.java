package petppy.domain.user;

import lombok.*;
import petppy.domain.BaseTimeEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;
import static petppy.domain.user.Rating.NONE;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(exclude = "member")
public class Membership extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "membership_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @Builder
    public Membership(User user) {
        this.user = user;
        this.rating = NONE;
    }


    public void changeRating(Rating rating) {
        this.rating = rating;
    }
}
