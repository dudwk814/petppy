package petppy.repository.enquiry;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.QEnquiry;

import static petppy.domain.user.QMembership.membership;
import static petppy.domain.user.QUser.user;

import petppy.domain.user.QMembership;
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

    /**
     * 회원용 문의 리스트 조회 (로그인한 본인의 문의만 조회 가능)
     * @param enquiryDTO
     * @param pageRequestDTO
     * @return
     */
    @Override
    public Page<Enquiry> findEnquiryListWithPaging(EnquiryDTO enquiryDTO, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Enquiry> results = queryFactory
                .selectFrom(enquiry)
                .where(enquiry.user.email.eq(enquiryDTO.getUserEmail()), enquiryTypeEq(enquiryDTO), enquiryStatusEq(enquiryDTO))
                .leftJoin(enquiry.user, user)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(enquiry.id.desc())
                .fetchResults();

        List<Enquiry> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * 관리자용 문의 리스트 조회 (모든 회원의 문의 조회)
     *
     * @param enquiryDTO
     * @param pageRequestDTO
     * @return
     */
    @Override
    public Page<Enquiry> searchEnquiryList(EnquiryDTO enquiryDTO, PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());
        QueryResults<Enquiry> results = queryFactory
                .selectFrom(enquiry)
                .where(
                        enquiryStatusEq(enquiryDTO),
                        enquiryTypeEq(enquiryDTO),
                        keywordContains(pageRequestDTO)
                        )
                .leftJoin(enquiry.user, user)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(enquiry.id.desc())
                .fetchResults();

        List<Enquiry> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }
    private BooleanExpression ConditionAll(EnquiryDTO enquiryDTO, PageRequestDTO requestDTO) {
        return emailEq(enquiryDTO).or(enquiryTypeEq(enquiryDTO)).or(enquiryTypeEq(enquiryDTO)).or(keywordContains(requestDTO));
    }

    private BooleanExpression emailEq(EnquiryDTO enquiryDTO) {  // 이메일 동일 조건
        if (enquiryDTO.getUserEmail() == null) {
            return null;
        }

        return enquiry.user.email.eq(enquiryDTO.getUserEmail());
    }

    private BooleanExpression enquiryTypeEq(EnquiryDTO enquiryDTO) {    // 문의타입 동일 조건

        if (enquiryDTO.getEnquiryType() == null) {
            return null;
        }

        return enquiry.enquiryType.eq(enquiryDTO.getEnquiryType());
    }

    private BooleanExpression enquiryStatusEq(EnquiryDTO enquiryDTO) {  // 문의상태 동일 조건
        if (enquiryDTO.getEnquiryStatus() == null) {
            return null;
        }

        return enquiry.enquiryStatus.eq(enquiryDTO.getEnquiryStatus());
    }

    private BooleanExpression keywordContains(PageRequestDTO requestDTO) {  // 키워드 포함 조건

        if (requestDTO.getKeyword() == null) {
            return null;
        }

        return enquiry.user.name.contains(requestDTO.getKeyword())
                .or(enquiry.user.email.contains(requestDTO.getKeyword()))
                .or(enquiry.title.contains(requestDTO.getKeyword()));
    }

}
