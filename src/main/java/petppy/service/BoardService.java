package petppy.service;

import petppy.domain.Board;
import petppy.domain.user.User;
import petppy.dto.BoardDto;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;

public interface BoardService {

    public void createBoard(BoardDto dto);

    public void modifyBoard(BoardDto dto);

    public void deleteBoard(Long id);

    public PageResultDTO<BoardDto, Board> searchBoardList(PageRequestDTO requestDTO);

    public BoardDto searchBoard(Long id);

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
