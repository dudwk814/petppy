package petppy.repository.email;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.email.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {

}
