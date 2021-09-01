package petppy.domain;

import lombok.*;
import petppy.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
@ToString(exclude = {"member"})
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    private String title;
    private double rating;
    private String content;
    private String image1;
    private String image2;
    private String image3;

    /*@Builder
    public Review(Member member, Order order, Item item, String title, double rating, String content) {
        this(member, order, item, title, rating, content, null, null, null);
    }*/

    @Builder()
    public Review(User user,  String title, double rating, String content, String image1, String image2, String image3) {
        this.user = user;
        this.title = title;
        this.rating = rating;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    /**
     * Review Entity 수정
     */
    public void changeReview(String title, String content, double rating, String image1, String image2, String image3) {
        this.title = title;
        this.rating = rating;
        this.content = content;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }
}
