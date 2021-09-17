package petppy.repository.board;

import org.springframework.data.domain.Page;
import petppy.domain.board.Board;
import petppy.dto.PageRequestDTO;

public interface BoardRepositoryCustom {

    Page<Board> searchBoardList(PageRequestDTO requestDTO);
}
