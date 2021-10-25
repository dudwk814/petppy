package petppy.domain.user;

import lombok.*;
import petppy.domain.BaseTimeEntity;
import petppy.exception.NotEnoughServiceCountException;

import javax.persistence.*;

import java.time.LocalDateTime;

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


    private int dogWalkCount = 0;
    private int petGroomingCount = 0;
    private int vetVisit = 0;

    @Builder
    public Membership(User user) {
        this.user = user;
        this.rating = NONE;
    }


    /**
     * 멤버십 등급 변경과 변경된 등급에 따른 서비스 사용횟수 변경
     * @param rating
     */
    public void changeRating(Rating rating) {
        this.rating = rating;

        // 각 rating 등급에 따른 서비스 횟수 초기화
        if (rating == Rating.PERSONAL) {
            changeServiceCount(3, 1, 1);
        } else if (rating == Rating.BUSINESS) {
            changeServiceCount(5, 3, 3);
        } else if (rating == Rating.ULTIMATE) {
            changeServiceCount(15, 5, 5);
        } else {    // 모든 조건이 false라면 NONE등급
            changeServiceCount(0,0,0);
        }
    }

    /**
     * 멤버십 서비스 사용가능 횟수 변경
     * @param dogWalkCount
     * @param petGroomingCount
     * @param vetVisitCount
     */
    public void changeServiceCount(int dogWalkCount, int petGroomingCount, int vetVisitCount) {
        this.dogWalkCount = dogWalkCount;
        this.petGroomingCount = petGroomingCount;
        this.vetVisit = vetVisitCount;
    }

    /**
     * 예약 생성시 각 서비스 이용가능 횟수 차감
     * 이용가능 횟수가 1보다 작으면 NotEnoughServiceCountException
     * @param servicesId
     */
    public void minusServiceCount(Long servicesId) {

        if (servicesId == 1) {
            if (this.dogWalkCount >= 1) {
                this.dogWalkCount -= 1;
            } else {
                throw new NotEnoughServiceCountException("이용가능한 서비스 횟수를 모두 사용하셨습니다.");
            }

        } else if (servicesId == 2) {

            if (this.petGroomingCount >= 1) {
                this.petGroomingCount -= 1;
            } else {
                throw new NotEnoughServiceCountException("이용가능한 서비스 횟수를 모두 사용하셨습니다.");
            }

        } else if (servicesId == 3) {
            if (this.vetVisit >= 1) {
                this.vetVisit -= 1;
            } else {
                throw new NotEnoughServiceCountException("이용가능한 서비스 횟수를 모두 사용하셨습니다.");
            }
        }
    }


    /**
     * 예약 취소하면 servicesId에 따른 서비스 이용가능 횟수 증가
     * @param servicesId
     */
    public void plusServiceCount(Long servicesId) {
        if (servicesId == 1) {
            this.dogWalkCount += 1;

        } else if (servicesId == 2) {

            this.petGroomingCount += 1;

        } else if (servicesId == 3) {
            this.vetVisit += 1;
        }
    }
}
