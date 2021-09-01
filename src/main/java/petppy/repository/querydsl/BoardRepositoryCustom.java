package petppy.repository.querydsl;

import org.springframework.data.domain.Page;
import petppy.domain.Board;
import petppy.dto.BoardDto;
import petppy.dto.PageRequestDTO;

import java.util.List;

public interface BoardRepositoryCustom {

    Page<Board> searchBoardList(PageRequestDTO requestDTO);
}
