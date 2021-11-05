package petppy.repository.user;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.board.Board;
import petppy.domain.user.QMembership;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.user.UserDTO;

import javax.persistence.EntityManager;

import java.util.List;

import static petppy.domain.board.QBoard.board;
import static petppy.domain.user.QMembership.membership;
import static petppy.domain.user.QUser.user;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 유저 검색 with 페이징 (어드민)
     * @param pageRequestDTO
     * @return
     */
    @Override
    public Page<User> searchUser(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("id").descending());

        QueryResults<User> results = queryFactory
                .select(user)
                .from(user)
                .leftJoin(user.membership, membership)
                .fetchJoin()
                .where(
                        ratingEq(pageRequestDTO),
                        keywordEq(pageRequestDTO)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(user.id.asc())
                .fetchResults();

        List<User> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /**
     * rating 검색조건
     * @param pageRequestDTO
     * @return
     */
    private BooleanExpression ratingEq(PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getRating() == null) {
            return null;
        }

        return user.membership.rating.eq(pageRequestDTO.getRating());
    }

    private BooleanExpression keywordEq(PageRequestDTO pageRequestDTO) {

        if (pageRequestDTO.getKeyword() == null) {
            return null;
        }

        return user.name.contains(pageRequestDTO.getKeyword()).or(user.email.contains(pageRequestDTO.getKeyword()));
    }

}
