package petppy.service.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.board.Board;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;
import petppy.domain.user.Membership;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.board.BoardDto;
import petppy.dto.reserve.ReserveDTO;
import petppy.exception.ReserveNotFoundException;
import petppy.exception.UserNotFoundException;
import petppy.repository.reserve.ReserveRepository;
import petppy.repository.user.MembershipRepository;
import petppy.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    /**
     * ReserveType이 Reserve인 예약 카운트
     * @param reserveType
     * @return
     */
    @Override
    public Long countReserveToReserveTypeEqualReserve(ReserveType reserveType) {

        return reserveRepository.countByReserveType(reserveType);
    }


    @Override
    @Transactional
    public ReserveDTO createReserve(ReserveDTO dto) {

        dto.setReserveType(ReserveType.RESERVE);    // reserve 생성시 reserveType은 RESERVE

        Reserve reserve = reserveRepository.save(dtoToEntity(dto)); // 예약등록

        User user = userRepository.findById(dto.getUserId()).orElseThrow(UserNotFoundException::new); // 유저 조회

        Membership membership = membershipRepository.findByUser(user);  // 유저 멤버십 조회

        membership.minusServiceCount(dto.getServicesId());



        return entityToDTO(reserve);
    }

    @Override
    public ReserveDTO findReserve(Long id) {

        return entityToDTO(findReserveOrElseThrow(id));
    }

    @Override
    public PageResultDTO<ReserveDTO, Reserve> findReserveList(ReserveDTO reserveDTO, PageRequestDTO requestDTO) {

        Page<Reserve> result = reserveRepository.findReserveList(reserveDTO, requestDTO);

        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();



        Function<Reserve, ReserveDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);

    }

    /**
     * 예약 취소
     * @param reserveDTO
     */
    @Override
    @Transactional
    public void cancelReserve(ReserveDTO reserveDTO) {
        findReserveOrElseThrow(reserveDTO.getId()).cancelReserve();

        membershipRepository.findByUser(
                userRepository.findById(reserveDTO.getUserId()).orElseThrow(UserNotFoundException::new)
        ).plusServiceCount(reserveDTO.getServicesId());
    }

    @Override
    @Transactional
    public void modifyReserveTime(Long id, LocalDateTime reserveStartDate) {
        findReserveOrElseThrow(id).changeReserveTime(reserveStartDate);
    }

    public Reserve findReserveOrElseThrow(Long id) {    // id로 reserve 검색
        return reserveRepository.findById(id).orElseThrow(ReserveNotFoundException::new);
    }
}
