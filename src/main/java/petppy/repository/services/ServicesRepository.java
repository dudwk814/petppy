package petppy.repository.services;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.services.Services;

public interface ServicesRepository extends JpaRepository<Services, Long> {

}
