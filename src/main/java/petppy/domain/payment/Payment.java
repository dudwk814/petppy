package petppy.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import petppy.domain.BaseTimeEntity;
import petppy.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Column(name = "pg")
    private String pg;  // 결제사

    @Column(name = "settle_case")
    private String settleCase = "card";  //결제 방식

    @Column(name = "detail_msg")
    private String detailMessage; // 결제 상세

    @Column(name = "price")
    private int price;  // 결제 금액

    @Column(name = "t_no")
    private String transactionNumber; // 거래 번호

    @Column(name = "app_no")
    private String approvalNumber; // 승인 번호

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus; // 거래 성공 여부

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 거래 회원


}
