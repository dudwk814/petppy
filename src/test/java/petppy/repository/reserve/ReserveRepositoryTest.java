package petppy.repository.reserve;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.Services;
import petppy.domain.user.Role;
import petppy.domain.user.User;
import petppy.repository.services.ServicesRepository;
import petppy.repository.user.UserRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

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