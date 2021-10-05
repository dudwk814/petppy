package petppy.repository.reserve;

import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.reserve.Reserve;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    public List<Reserve> findByUserEmail(String email);
}
