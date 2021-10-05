package petppy.service.reserve;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.dto.reserve.ReserveDTO;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReserveServiceTest {

    @Autowired
    ReserveService reserveService;

    @Test
    @Commit
    public void 예약_생성() throws Exception {
        //given

        //when

        //then
    }
}