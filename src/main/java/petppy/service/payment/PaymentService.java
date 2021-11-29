package petppy.service.payment;

import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.payment.PaymentDTO;

public interface PaymentService {

    public void createPaymentInfo(PaymentDTO paymentDTO);

    public PaymentDTO findPaymentByEmail(String email);

    public PageResultDTO<PaymentDTO, Payment> findPaymentListByEmail(PaymentDTO paymentDTO, PageRequestDTO requestDTO);

    public void cancel(String imp_uid);

    default PaymentDTO entityToDTO(Payment payment) {
        return PaymentDTO
                .builder()
                .email(payment.getUser().getEmail())
                .id(payment.getId())
                .detailMessage(payment.getDetailMessage())
                .approvalNumber(payment.getApprovalNumber())
                .pg(payment.getPg())
                .transactionNumber(payment.getTransactionNumber())
                .userId(payment.getUser().getId())
                .price(payment.getPrice())
                .build();
    }

    default Payment dtoToEntity(PaymentDTO paymentDTO) {

        // 결제 성공 여부에 따른 분기
        if (paymentDTO.isPaymentStatus()) {
            return Payment
                    .builder()
                    .user(User.builder().id(paymentDTO.getUserId()).build())
                    .paymentStatus(PaymentStatus.SUCCESS)
                    .pg(paymentDTO.getPg())
                    .price(paymentDTO.getPrice())
                    .detailMessage(paymentDTO.getDetailMessage())
                    .transactionNumber(paymentDTO.getTransactionNumber())
                    .approvalNumber(paymentDTO.getApprovalNumber())
                    .settleCase("CARD")
                    .build();
        } else {
            return Payment
                    .builder()
                    .user(User.builder().id(paymentDTO.getUserId()).build())
                    .paymentStatus(PaymentStatus.FAIL)
                    .pg(paymentDTO.getPg())
                    .price(paymentDTO.getPrice())
                    .detailMessage(paymentDTO.getDetailMessage())
                    .transactionNumber(paymentDTO.getTransactionNumber())
                    .approvalNumber(paymentDTO.getApprovalNumber())
                    .settleCase("CARD")
                    .build();
        }


    }
}
