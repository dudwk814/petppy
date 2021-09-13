package petppy.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.Board;
import petppy.domain.QBoard;
import petppy.domain.user.QUser;
import petppy.dto.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.QBoardDto;

import javax.persistence.EntityManager;
import java.util.List;

import static petppy.domain.QBoard.board;
import static petppy.domain.user.QUser.user;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Board> searchBoardList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());

        QueryResults<Board> results = queryFactory
                .select(board)
                .from(board)
                .leftJoin(board.user, user)
                .fetchJoin()
                .distinct()
                .where(
                        typeContains(requestDTO)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.id.desc())
                .fetchResults();

        List<Board> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanBuilder typeContains(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();

        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression expression = board.id.gt(0L);

        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {    // 검색조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(board.title.contains(keyword));
        }

        if (type.contains("c")) {
            conditionBuilder.or(board.content.contains(keyword));
        }

        if (type.contains("w")) {
            conditionBuilder.or(board.user.email.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    private BooleanExpression titleContains(PageRequestDTO requestDTO) {    // 게시글 제목 포함 조건

        if (!requestDTO.getType().contains("t")) {
            return null;
        }

        return board.title.contains(requestDTO.getKeyword());
    }

    private BooleanExpression contentContains(PageRequestDTO requestDTO) {    // 게시글 내용 포함 조건

        if (!requestDTO.getType().contains("c")) {
            return null;
        }

        return board.content.contains(requestDTO.getKeyword());
    }

   /* private BooleanExpression memberIdContains(PageRequestDTO requestDTO) {    // 게시글 내용 포함 조건

        if (!requestDTO.getType().contains("w")) {
            return null;
        }

        return board.member.id.contains(requestDTO.getKeyword());
    }*/

}
