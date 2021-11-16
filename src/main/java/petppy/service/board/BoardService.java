package petppy.service.board;

import org.springframework.data.domain.Pageable;
import petppy.domain.board.Board;
import petppy.domain.user.User;
import petppy.dto.board.BoardDTO;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;

import java.util.List;

public interface BoardService {

    public void createBoard(BoardDTO dto);

    public void modifyBoard(BoardDTO dto);

    public void deleteBoard(Long id);

    public Long commentCount(Long id);

    public PageResultDTO<BoardDTO, Board> searchBoardList(PageRequestDTO requestDTO);

    public BoardDTO searchBoard(Long id);

    public List<BoardDTO> findRecentBoardList(Pageable pageable);

    public PageResultDTO<BoardDTO, Board> findByUserEmail(PageRequestDTO requestDTO, String email);

    default BoardDTO entityToDto(Board board) {
        return BoardDTO
                .builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(Integer.valueOf(board.getHit()))
                .commentCount(Integer.valueOf(board.getCommentCount()))
                .email(board.getUser().getEmail())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getModifiedDate())
                .build();
    }

    default Board dtoToEntity(BoardDTO dto) {
        return Board
                .builder()
                .id(dto.getBoardId())
                .user(
                        User
                                .builder()
                                .id(dto.getUserId())
                                .email(dto.getEmail())
                                .build()
                ).title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }
}
