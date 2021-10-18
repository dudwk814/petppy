package petppy.repository.reserve;

import org.springframework.data.domain.Page;
import petppy.domain.reserve.Reserve;
import petppy.dto.PageRequestDTO;
import petppy.dto.reserve.ReserveDTO;

public interface ReserveRepositoryCustom {

    Page<Reserve> findReserveList(ReserveDTO reserveDTO, PageRequestDTO pageRequestDTO);
}
