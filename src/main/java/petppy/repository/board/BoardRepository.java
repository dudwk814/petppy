package petppy.repository.board;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import petppy.domain.board.Board;
import petppy.repository.board.BoardRepositoryCustom;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>, BoardRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Board> findById(Long aLong);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select b from Board b order by b.id desc")
    List<Board> findRecentBoardList(Pageable pageable);

    @Query("select b.commentCount from Board b where b.id = :id")
    Long countComment(@Param("id") Long id);

    void deleteByUserId(Long id);

}
