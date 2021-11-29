package petppy.repository.payment;

import org.springframework.data.domain.Page;
import petppy.domain.payment.Payment;
import petppy.dto.PageRequestDTO;
import petppy.dto.payment.PaymentDTO;

public interface PaymentRepositoryCustom {

    Page<Payment> findPaymentListByEmail(PaymentDTO paymentDTO, PageRequestDTO requestDTO);
}
