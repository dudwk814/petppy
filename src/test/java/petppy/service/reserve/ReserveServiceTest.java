package petppy.service.reserve;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.Address;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.reserve.ReserveDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReserveServiceTest {

    @Autowired
    ReserveService reserveService;

    @Test
    @Commit
    public void 예약_취소() throws Exception {
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .id(2L)
                .userId(1L)
                .servicesId(1L).build();

        reserveService.cancelReserve(reserveDTO);
    }

    @Test
    public void 예약중인_건수_조회() throws Exception {

        Long result = reserveService.countReserveToReserveTypeEqualReserve(ReserveType.RESERVE);

        assertEquals(1, result);
    }

    @Test
    public void 관리자용_예약_조회() throws Exception {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        ReserveDTO reserveDTO = ReserveDTO.builder().reserveType(ReserveType.CANCEL).servicesType(ServicesType.DOG_WALK).build();
        PageResultDTO<ReserveDTO, Reserve> result = reserveService.searchReserve(reserveDTO, pageRequestDTO);

        List<ReserveDTO> content = result.getDtoList();
        for (ReserveDTO dto : content) {
            System.out.println("dto = " + dto);
        }

        System.out.println("result = " + result.getSize());
    }

    
}