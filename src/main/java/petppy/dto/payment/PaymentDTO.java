package petppy.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import petppy.domain.payment.PaymentStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    private Long id;

    private String pg;

    private String detailMessage;

    private int price;

    private String transactionNumber;

    private String approvalNumber;

    private boolean paymentStatus;

    private String email;

    private Long userId;

    private LocalDateTime createdDate;

}
