package petppy.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;
import petppy.dto.payment.PaymentDTO;
import petppy.exception.PaymentNotFoundException;
import petppy.repository.payment.PaymentRepository;

import static petppy.domain.payment.PaymentStatus.SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void cancel(String imp_uid) {
        Payment payment = paymentRepository
                .findByTransactionNumber(imp_uid)
                .orElseThrow(PaymentNotFoundException::new);

        payment.cancel();
    }

    @Override
    public PaymentDTO findPaymentByEmail(String email) {


        Payment payment = paymentRepository
                .findFirstByUserEmailAndPaymentStatusOrderByIdDesc(email, SUCCESS)
                .orElseThrow(PaymentNotFoundException::new);

        return entityToDTO(payment);
    }

    @Override
    public void createPaymentInfo(PaymentDTO paymentDTO) {
        paymentRepository.save(dtoToEntity(paymentDTO));
    }
}
