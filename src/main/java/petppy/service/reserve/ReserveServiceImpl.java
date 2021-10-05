package petppy.service.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.exception.ReserveNotFoundException;
import petppy.repository.reserve.ReserveRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;

    @Override
    public ReserveDTO createReserve(ReserveDTO dto) {

        dto.setReserveType(ReserveType.RESERVE);    // reserve 생성시 reserveType은 RESERVE

        // 각 서비스 타입에 따라 종료일(setReserveEndDate) 설정
        /*if (dto.getServicesType() == ServicesType.CAT_SITTING) {
            dto.setReserveEndDate(dto.getReserveStartDate().plusHours(2));
        }*/

        Reserve reserve = reserveRepository.save(dtoToEntity(dto));

        return entityToDTO(reserve);
    }

    @Override
    public ReserveDTO findReserve(Long id) {

        return entityToDTO(findReserveOrElseThrow(id));
    }

    @Override
    public PageResultDTO<ReserveDTO, Reserve> findReserveListWithPaging(String email, PageRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void cancelReserve(Long id) {
        findReserveOrElseThrow(id).cancelReserve();
    }

    @Override
    public void modifyReserveTime(Long id, LocalDateTime reserveStartDate) {
        findReserveOrElseThrow(id).changeReserveTime(reserveStartDate);
    }

    public Reserve findReserveOrElseThrow(Long id) {    // id로 reserve 검색
        return reserveRepository.findById(id).orElseThrow(ReserveNotFoundException::new);
    }
}
