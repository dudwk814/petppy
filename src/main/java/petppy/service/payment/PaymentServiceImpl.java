package petppy.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.enquiry.EnquiryDTO;
import petppy.dto.payment.PaymentDTO;
import petppy.exception.PaymentNotFoundException;
import petppy.repository.payment.PaymentRepository;

import java.util.function.Function;

import static petppy.domain.payment.PaymentStatus.SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PageResultDTO<PaymentDTO, Payment> findPaymentListByEmail(PaymentDTO paymentDTO, PageRequestDTO requestDTO) {
        Page<Payment> result = paymentRepository.findPaymentListByEmail(paymentDTO, requestDTO);
        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();

        Function<Payment, PaymentDTO> fn = (payment -> entityToDTO(payment));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
    }

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
