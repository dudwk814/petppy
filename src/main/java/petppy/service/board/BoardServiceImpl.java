package petppy.service.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.board.Board;
import petppy.domain.board.QBoard;
import petppy.dto.board.BoardDTO;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.exception.BoardNotFoundException;
import petppy.repository.board.BoardRepository;
import petppy.repository.comment.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public void createBoard(BoardDTO dto) {
        boardRepository.save(dtoToEntity(dto));
    }

    @Override
    @Transactional
    public void modifyBoard(BoardDTO dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(BoardNotFoundException::new);
        board.changeBoard(dto.getTitle(), dto.getContent());
    }

    @Override
    @Transactional
    public void deleteBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);

        if (board.getCommentCount() >= 1) {
            commentRepository.deleteByBoardIdAndParentNotNull(id);
            commentRepository.deleteByBoardId(id);
        }
        boardRepository.deleteById(id);
    }

    @Override
    public Long commentCount(Long id) {
        return boardRepository.countComment(id);
    }

    @Override
    public PageResultDTO<BoardDTO, Board> searchBoardList(PageRequestDTO requestDTO) {

        Page<Board> result = boardRepository.searchBoardList(requestDTO);

        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();



        Function<Board, BoardDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
    }

    @Override
    @Transactional
    public BoardDTO searchBoard(Long id) {

        Optional<Board> result = boardRepository.findById(id);

        if (result.isPresent()) {

            Board findBoard = result.get();

            findBoard.changeHitCount();

            return entityToDto(findBoard);
        } else {
            return null;
        }
    }

    /**
     * 최근 5개 게시글 조회
     */
    @Override
    public List<BoardDTO> findRecentBoardList(Pageable pageable) {
        List<Board> result = boardRepository.findRecentBoardList(pageable);

        return result
                .stream()
                .map(board -> entityToDto(board))
                .collect(Collectors.toList());
    }

    /**
     * 유저 이메일로 게시글 조회
     */
    @Override
    public PageResultDTO<BoardDTO, Board> findByUserEmail(PageRequestDTO requestDTO, String email) {

        Page<Board> result = boardRepository.findUserEmail(requestDTO, email);

        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();

        Function<Board, BoardDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
    }

    /**
     * querydsl 검색 조건
     * JPAQueryFactory 방식으로 변경
     */
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QBoard qBoard = QBoard.board;

        BooleanExpression expression = qBoard.id.gt(0L);    // id > 0 조건만 검색

        booleanBuilder.and(expression);

        if (type == null || keyword == null) {  // 검색 조건 없는 경우
            return booleanBuilder;
        }

        // 검색 조건 (게시글 타입, 검색어)
        BooleanBuilder searchCondition = new BooleanBuilder();

        if (type.contains("t")) {   // 타입 : 제목
            searchCondition.or(qBoard.title.contains(keyword));
        }

        if (type.contains("c")) {   // 타입 : 내용
            searchCondition.or(qBoard.content.contains(keyword));
        }

       /* if (type.contains("w")) {   // 타입 : 작성자
            searchCondition.or(qBoard.member.id.contains(keyword));
        }*/

        // 조건 통합
        booleanBuilder.and(searchCondition);

        return booleanBuilder;
    }
}
