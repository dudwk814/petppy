package petppy.repository.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.payment.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
