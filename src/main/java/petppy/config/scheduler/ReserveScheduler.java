package petppy.config.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.repository.reserve.ReserveRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(readOnly = true)
@Log4j2
@RequiredArgsConstructor
public class ReserveScheduler {

    private final ReserveRepository reserveRepository;

    /**
     * 매일 오전 0시에 예약 날짜 지나고 예약상태가 RESERVE인 예약의 예약상태 COMPLETE로 변경
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void changeReserveStatus() {
        List<Reserve> result = reserveRepository.findByReserveTypeAndReserveEndDateBefore(ReserveType.RESERVE, LocalDateTime.now());

        List<Long> ids = new ArrayList<>();

        result.stream().forEach(reserve -> ids.add(reserve.getId()));

        reserveRepository.changeReserveTypeToComplete(ReserveType.COMPLETE, ids);
    }
}
