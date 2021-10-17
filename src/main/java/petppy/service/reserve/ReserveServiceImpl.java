package petppy.service.reserve;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReserveServiceImpl implements ReserveService {

    private final ReserveRepository reserveRepository;
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

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
