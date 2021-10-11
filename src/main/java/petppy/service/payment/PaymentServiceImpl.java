package petppy.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.dto.payment.PaymentDTO;
import petppy.repository.payment.PaymentRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public void createPaymentInfo(PaymentDTO paymentDTO) {
        paymentRepository.save(dtoToEntity(paymentDTO));
    }
}
