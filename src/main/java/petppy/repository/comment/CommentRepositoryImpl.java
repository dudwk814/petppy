package petppy.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import petppy.domain.Comment;
import petppy.domain.QComment;
import petppy.domain.user.QUser;

import javax.persistence.EntityManager;
import java.util.List;

import static petppy.domain.QComment.comment;
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
}
