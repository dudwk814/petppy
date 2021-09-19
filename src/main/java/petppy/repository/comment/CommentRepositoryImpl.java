package petppy.repository.comment;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.comment.Comment;
import petppy.dto.PageRequestDTO;

import javax.persistence.EntityManager;
import java.util.List;

import static petppy.domain.comment.QComment.comment;
import static petppy.domain.user.QUser.user;


public class CommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Comment> findCommentByBoardId(Long boardId) {
        return queryFactory
                .selectFrom(comment)
                .leftJoin(comment.parent)
                .leftJoin(comment.user, user)
                .fetchJoin()
                .where(comment.board.id.eq(boardId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdDate.asc()
                ).fetch();
    }

    @Override
    public Page<Comment> findCommentByBoardIdWithPaging(Long boardId, PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("id").descending());
        QueryResults<Comment> results = queryFactory
                .selectFrom(comment)
                .leftJoin(comment.user, user)
                .fetchJoin()
                .where(
                        comment.board.id.eq(boardId),
                        comment.parent.isNull())
                .orderBy(comment.createdDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Comment> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
