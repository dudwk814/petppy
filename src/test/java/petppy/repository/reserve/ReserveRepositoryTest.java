package petppy.repository.reserve;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.Services;
import petppy.domain.services.ServicesType;
import petppy.domain.user.Role;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.reserve.ReserveCountDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.repository.services.ServicesRepository;
import petppy.repository.user.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static petppy.domain.services.ServicesType.CAT_SITTING;

@SpringBootTest
@Transactional
class ReserveRepositoryTest {

    @Autowired
    ReserveRepository reserveRepository;

    @Autowired
    ServicesRepository servicesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    @Test
    @Commit
    public void 예약_생성() throws Exception {

        addReserve();

    }

    @Test
    public void 서비스별_예약() throws Exception {
        List<ReserveCountDTO> result = reserveRepository.countByServicesType(ReserveType.RESERVE);

        for (ReserveCountDTO reserveCountDTO : result) {
            System.out.println("reserveCountDTO = " + reserveCountDTO);
        }
    }

    @Test
    public void 관리자_예약_조회() throws Exception {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        ReserveDTO reserveDTO = ReserveDTO.builder().reserveType(ReserveType.CANCEL).servicesType(ServicesType.DOG_WALK).build();

        Page<Reserve> reserves = reserveRepository.searchReserve(reserveDTO, pageRequestDTO);
        for (Reserve reserve : reserves) {
            System.out.println("reserve = " + reserve);
        }
    }

    @Test
    public void 예약_조회_all() throws Exception {
        //given
        for (int i = 0; i < 100; i++) {
            addReserve();
        }

        //when
        List<Reserve> result = reserveRepository.findAll();

        //then
        assertEquals(100, result.size()); // addReserve() * 100 총 100건의 예약
    }

    @Test
    public void 예약_조회_email() throws Exception {
        //given
        for (int i = 0; i < 10; i++) {
            addReserve();
        }

        //when
        List<Reserve> result = reserveRepository.findByUserEmail("test@test.com");

        //then
        assertEquals(10, result.size()); // addReserve() * 10 총 100건의 예약
    }

    @Test
    public void 예약_조회_emailAndType() throws Exception {
        ReserveDTO reserveDTO = ReserveDTO.builder()
                .reserveType(ReserveType.RESERVE)
                .email("kj99658103@gmail.com")
                .reserveStartDate("2021-10-20-13-00")
                .build();

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        Page<Reserve> reserveList = reserveRepository.findReserveList(reserveDTO, pageRequestDTO);

        List<Reserve> result = reserveList.getContent();

        System.out.println("result = " + result);
    }

    @Test
    public void 예약_취소() throws Exception {
        //given
        Reserve savedReserve = addReserve();

        //when
        savedReserve.cancelReserve();

        em.flush();
        em.clear();

        //then
        Reserve reserve = reserveRepository.findById(1L).get();

        assertEquals(ReserveType.CANCEL, reserve.getReserveType()); // CANCEL

    }

    @Test
    @Commit
    public void 예약_시간_변경 () throws Exception {
        //given
        Reserve reserve = addReserve();

        //when
        reserve.changeReserveTime(LocalDateTime.now());

    }

    @Test
    public void 예약날짜_지난_예약_조회() throws Exception {
        List<Reserve> result = reserveRepository.findByReserveTypeAndReserveEndDateBefore(ReserveType.RESERVE, LocalDateTime.now());

        for (Reserve reserve : result) {
            System.out.println("reserve.getReserveStartDate() = " + reserve.getReserveStartDate());
            System.out.println("reserve.getUser().getName() = " + reserve.getUser().getName());
        }
    }

    @Test
    public void 예약날짜_지난_예약_예약상태_COMPLETE로_변경() throws Exception {
        List<Reserve> result = reserveRepository.findByReserveTypeAndReserveEndDateBefore(ReserveType.RESERVE, LocalDateTime.now());

        List<Long> ids = new ArrayList<>();
        ReserveType reserveType = ReserveType.COMPLETE;

        result.stream().forEach(reserve -> ids.add(reserve.getId()));

        reserveRepository.changeReserveTypeToComplete(reserveType, ids);

    }

    @Test
    public void 특정_날짜_예약_조회() throws Exception {
        LocalDateTime start = LocalDateTime.of(2021, 12, 07, 0, 0);
        LocalDateTime end = LocalDateTime.of(2021, 12, 07, 23, 59, 59);

        List<Reserve> result = reserveRepository.findReserveByReserveStartDateBetweenAndServicesServicesType(start, end, ServicesType.DOG_WALK);

        for (Reserve reserve : result) {
            System.out.println("reserve = " + reserve);
        }
    }

    @Test
    @Commit
    public void 회원번호로_예약삭제() throws Exception {
        User findUser = userRepository.findByEmail("admin@123.com").get();

        reserveRepository.deleteByUserId(findUser.getId());
    }

    private User addUser() {

        return userRepository.save(User
                .builder()
                .role(Role.MEMBER)
                .email("test@test.com")
                .name("testUser").build());
    }

    private Services addServices() {

        Services services = Services
                .builder()
                .servicesType(CAT_SITTING)
                .build();

        return servicesRepository.save(services);
    }

    private Reserve addReserve() {

        User user = addUser();

        Services services = addServices();

        return reserveRepository.save(Reserve
                .builder()
                .reserveStartDate(LocalDateTime.of(2021, 10, 5, 11, 0))
                .reserveEndDate(LocalDateTime.of(2021, 10, 5, 13, 0))
                .user(user)
                .services(services)
                .build());
    }
}