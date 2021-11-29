package petppy.repository.payment;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.notificaton.Notification;
import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;
import petppy.domain.payment.QPayment;
import petppy.domain.user.QUser;
import petppy.dto.PageRequestDTO;
import petppy.dto.payment.PaymentDTO;

import javax.persistence.EntityManager;
import java.util.List;

import static petppy.domain.notificaton.QNotification.notification;
import static petppy.domain.payment.QPayment.payment;
import static petppy.domain.user.QUser.user;

public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Payment> findPaymentListByEmail(PaymentDTO paymentDTO, PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Payment> results = queryFactory
                .selectFrom(payment)
                .where(payment.user.email.eq(paymentDTO.getEmail()), payment.paymentStatus.eq(PaymentStatus.SUCCESS))   // paymentDTO파라미터의 이메일과 유저 이메일 동등비교, PaymentStatus.SUCCESS조건
                .leftJoin(payment.user, user)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(payment.id.desc())
                .fetchResults();

        List<Payment> content = results.getResults();

        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
