package petppy.service.payment;

import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;
import petppy.domain.user.User;
import petppy.dto.payment.PaymentDTO;

public interface PaymentService {

    public void createPaymentInfo(PaymentDTO paymentDTO);

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
                    .build();
        }


    }
}
