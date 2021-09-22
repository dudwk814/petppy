package petppy.service.board;

import org.springframework.data.domain.Pageable;
import petppy.domain.board.Board;
import petppy.domain.user.User;
import petppy.dto.board.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;

import java.util.List;

public interface BoardService {

    public void createBoard(BoardDto dto);

    public void modifyBoard(BoardDto dto);

    public void deleteBoard(Long id);

    public PageResultDTO<BoardDto, Board> searchBoardList(PageRequestDTO requestDTO);

    public BoardDto searchBoard(Long id);

    public List<BoardDto> findRecentBoardList(Pageable pageable);

    public PageResultDTO<BoardDto, Board> findByUserEmail(PageRequestDTO requestDTO, String email);

    default BoardDto entityToDto(Board board) {
        return BoardDto
                .builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .hit(board.getHit())
                .commentCount(board.getCommentCount())
                .email(board.getUser().getEmail())
                .createdDate(board.getCreatedDate())
                .lastModifiedDate(board.getModifiedDate())
                .build();
    }

    default Board dtoToEntity(BoardDto dto) {
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
