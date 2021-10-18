package petppy.repository.reserve;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.notificaton.Notification;
import petppy.domain.reserve.Reserve;
import petppy.dto.PageRequestDTO;
import petppy.dto.reserve.ReserveDTO;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static petppy.domain.reserve.QReserve.reserve;

public class ReserveRepositoryImpl implements ReserveRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReserveRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Reserve> findReserveList(ReserveDTO reserveDTO, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Reserve> results = queryFactory
                .selectFrom(reserve)
                .where(reserve.user.email.eq(reserveDTO.getEmail()).and(startEq(reserveDTO)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reserve.id.desc())
                .fetchResults();

        List<Reserve> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }

    private BooleanExpression startEq(ReserveDTO reserveDTO) {

        if (reserveDTO.getReserveStartDate() == null) {
            return null;
        }

        String[] reserveStartDateToString = reserveDTO.getReserveStartDate().split("-");

        int[] reserveStartDateToInt = new int[5];

        for (int i = 0; i < reserveStartDateToString.length; i++) {

            reserveStartDateToInt[i] = Integer.parseInt(reserveStartDateToString[i]);
        }

        LocalDateTime reserveStartDate = LocalDateTime.of(
                reserveStartDateToInt[0], reserveStartDateToInt[1], reserveStartDateToInt[2], reserveStartDateToInt[3], reserveStartDateToInt[4]
        );

        return reserve.reserveStartDate.eq(reserveStartDate);
    }
}
