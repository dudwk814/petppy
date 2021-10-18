package petppy.repository.reserve;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveRepositoryCustom {

    public List<Reserve> findByUserEmail(String email);

    public List<Reserve> findByUserEmailAndReserveType(String email, ReserveType reserveType, Pageable pageable);
}
