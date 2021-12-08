package petppy.service.reserve;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;
import petppy.domain.user.Membership;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.exception.ReserveNotFoundException;
import petppy.exception.UserNotFoundException;
import petppy.repository.reserve.ReserveRepository;
import petppy.repository.user.MembershipRepository;
import petppy.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    /**
     * 회원 이메일과 예약일로 예약 조회
     * @param reserveDTO
     * @param requestDTO
     * @return
     */
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
     * 관리자용 예약 조회 (이름, 이메일, 예약 종류, 서비스 종류 등으로 예약 조회)
     * @param reserveDTO
     * @param requestDTO
     * @return
     */
    @Override
    public PageResultDTO<ReserveDTO, Reserve> searchReserve(ReserveDTO reserveDTO, PageRequestDTO requestDTO) {
        Page<Reserve> result = reserveRepository.searchReserve(reserveDTO, requestDTO);

        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();



        Function<Reserve, ReserveDTO> fn = (entity -> entityToDTO(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
    }

    @Override
    public List<Integer> findTimeNumberByDate(LocalDateTime start, ServicesType servicesType) {

        LocalDateTime end = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), 23, 59);// end = start 날짜의 23시 59분

        List<Reserve> result = reserveRepository.findReserveByReserveStartDateBetweenAndServicesServicesType(start, end, servicesType);
        List<ReserveDTO> reserveDTOList = result.stream().map(reserve -> entityToDTO(reserve)).collect(Collectors.toList());

        List timeNumberList = new ArrayList();

        for (ReserveDTO reserveDTO : reserveDTOList) {
            if (Integer.valueOf(reserveDTO.getTimeNumber()) != null) {
                timeNumberList.add(Integer.valueOf(reserveDTO.getTimeNumber()));
            }
        }

        return timeNumberList;
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
