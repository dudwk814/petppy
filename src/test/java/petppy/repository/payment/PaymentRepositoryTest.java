package petppy.repository.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;
import petppy.domain.user.User;
import petppy.exception.UserNotFoundException;
import petppy.repository.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentRepositoryTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Commit
    public void 결제_등록() throws Exception {

        paymentRepository.save(Payment
                .builder()
                .user(findUser())
                .pg("kakao")
                .detailMessage("결제완료")
                .paymentStatus(PaymentStatus.SUCCESS)
                .price(27000)
                .transactionNumber("T2951948331677870693")
                .approvalNumber("T2951948331677870693")
                .build());

    }

    private User findUser() {
        return userRepository.findByEmail("kj99658103@gmail.com").orElseThrow(UserNotFoundException::new);
    }
}