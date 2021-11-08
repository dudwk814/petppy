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
import petppy.domain.services.QServices;
import petppy.domain.user.QMembership;
import petppy.domain.user.QUser;
import petppy.dto.PageRequestDTO;
import petppy.dto.reserve.ReserveDTO;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static petppy.domain.reserve.QReserve.reserve;
import static petppy.domain.services.QServices.services;
import static petppy.domain.user.QMembership.membership;
import static petppy.domain.user.QUser.user;

public class ReserveRepositoryImpl implements ReserveRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReserveRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 회원 이메일과 예약일로 조건으로 예약 조회
     * @param reserveDTO
     * @param pageRequestDTO
     * @return
     */
    @Override
    public Page<Reserve> findReserveList(ReserveDTO reserveDTO, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Reserve> results = queryFactory
                .selectFrom(reserve)
                .leftJoin(reserve.services, services)
                .where(reserve.user.email.eq(reserveDTO.getEmail()).and(startEq(reserveDTO)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reserve.id.desc())
                .fetchResults();

        List<Reserve> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }

    /**
     * 관리자용 예약 검색
     * @param reserveDTO
     * @param pageRequestDTO
     * @return
     */
    @Override
    public Page<Reserve> searchReserve(ReserveDTO reserveDTO, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Reserve> results = queryFactory
                .selectFrom(reserve)
                .leftJoin(reserve.services, services)
                .leftJoin(reserve.user, user)
                .fetchJoin()
                .where(
                        keywordContains(pageRequestDTO),
                        reserveTypeEq(reserveDTO),
                        servicesTypeEq(reserveDTO))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reserve.id.desc())
                .fetchResults();

        List<Reserve> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression keywordContains(PageRequestDTO pageRequestDTO) {    // 회원 이름 혹은 이메일 검색 조건

        if (pageRequestDTO.getKeyword() == null) {
            return null;
        }

        return reserve.user.name.contains(pageRequestDTO.getKeyword()).or(reserve.user.email.contains(pageRequestDTO.getKeyword()));
    }

    private BooleanExpression reserveTypeEq(ReserveDTO reserveDTO) {    // 예약 타입(예약, 취소, 완료) 검색 조건

        if (reserveDTO.getReserveType() == null) {
            return null;
        }

        return reserve.reserveType.eq(reserveDTO.getReserveType());
    }

    private BooleanExpression servicesTypeEq(ReserveDTO reserveDTO) {   // 서비스 타입 검색 조건

        if (reserveDTO.getServicesType() == null) {
            return null;
        }
        return reserve.services.servicesType.eq(reserveDTO.getServicesType());
    }


    private BooleanExpression startEq(ReserveDTO reserveDTO) {  // 예약일 검색 조건

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
