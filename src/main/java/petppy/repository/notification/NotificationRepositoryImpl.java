package petppy.repository.notification;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.board.Board;
import petppy.domain.notificaton.IsRead;
import petppy.domain.notificaton.Notification;
import petppy.domain.notificaton.QNotification;
import petppy.dto.PageRequestDTO;

import javax.persistence.EntityManager;

import java.util.List;

import static petppy.domain.notificaton.QNotification.notification;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public NotificationRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Notification> findNotificationList(String targetEmail, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());


        QueryResults<Notification> results = queryFactory
                .selectFrom(notification)
                .where(notification.targetEmail.eq(targetEmail))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notification.id.desc())
                .fetchResults();

        List<Notification> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
