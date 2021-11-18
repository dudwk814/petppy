package petppy.repository.enquiry;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.QEnquiry;
import petppy.domain.notificaton.Notification;
import petppy.dto.PageRequestDTO;
import petppy.dto.enquiry.EnquiryDTO;

import javax.persistence.EntityManager;

import java.util.List;

import static petppy.domain.enquiry.QEnquiry.enquiry;
import static petppy.domain.notificaton.QNotification.notification;

public class EnquiryRepositoryImpl implements EnquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public EnquiryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Enquiry> findEnquiryList(EnquiryDTO enquiryDTO, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Enquiry> results = queryFactory
                .selectFrom(enquiry)
                .where(enquiry.user.email.eq(enquiryDTO.getUserEmail()))
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(enquiry.id.desc())
                .fetchResults();

        List<Enquiry> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
