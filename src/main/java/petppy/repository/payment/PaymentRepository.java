package petppy.repository.payment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petppy.domain.payment.Payment;
import petppy.domain.payment.PaymentStatus;

import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {

    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    public Optional<Payment> findFirstByUserEmailAndPaymentStatusOrderByIdDesc(String email,PaymentStatus paymentStatus);

    @EntityGraph(attributePaths = {"user"}, type = FETCH)
    public Optional<Payment> findByTransactionNumber(String imp_uid);

}
