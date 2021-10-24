package petppy.config.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.user.Membership;
import petppy.domain.user.Rating;
import petppy.repository.user.MembershipRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class MembershipScheduler {

    private final MembershipRepository membershipRepository;

    /**
     * 매일 새벽2시에 오늘 자로 끝나는 멤버십 등급 NONE으로 초기화
     */
    @Scheduled(cron = "000***")
    public void changeMembership() {
        List<Membership> result = membershipRepository.findAll();

        for (Membership membership : result) {
            if (membership.getRating() != Rating.NONE) {
                if (LocalDateTime.now().isAfter(membership.getModifiedDate().plusMonths(1))) {
                    log.info(membership.getUser().getEmail() + "님의 멤버십등급 기한이 끝나 멤버십 등급을 NONE으로 조정");
                    membership.changeRating(Rating.NONE);
                }
            }
        }
    }


}
