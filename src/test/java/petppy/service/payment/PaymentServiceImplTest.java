package petppy.service.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.payment.Payment;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.payment.PaymentDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Test
    void findPaymentListByEmail() {
        PaymentDTO paymentDTO = PaymentDTO.builder().email("kj99658103@gmail.com").build();
        PageRequestDTO requestDTO = new PageRequestDTO();

        PageResultDTO<PaymentDTO, Payment> result = paymentService.findPaymentListByEmail(paymentDTO, requestDTO);

        List<PaymentDTO> content = result.getDtoList();

        for (PaymentDTO dto : content) {
            System.out.println("dto = " + dto);
        }
    }
}