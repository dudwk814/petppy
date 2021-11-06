package petppy.repository.reserve;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.dto.reserve.ReserveCountDTO;
import petppy.dto.reserve.ReserveDTO;
import petppy.dto.user.MembershipCountDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

public interface ReserveRepository extends JpaRepository<Reserve, Long>, ReserveRepositoryCustom {

    public List<Reserve> findByUserEmail(String email);

    public List<Reserve> findByUserEmailAndReserveType(String email, ReserveType reserveType, Pageable pageable);

    // 예약일이 지난 예약 중 예약상태가 RESERVE인 예약 조회
    @EntityGraph(attributePaths = {"user", "user.membership", "services"}, type = FETCH)
    List<Reserve> findByReserveTypeAndReserveEndDateBefore(ReserveType reserveType, LocalDateTime reserveDate);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Reserve r set r.reserveType = :reserveType where r.id in :ids")
    void changeReserveTypeToComplete(@Param("reserveType") ReserveType reserveType, @Param("ids") List<Long> ids);

    Long countByReserveType(ReserveType reserveType);

    @Query("select new petppy.dto.reserve.ReserveCountDTO(count(r), r.services.servicesType) from Reserve r where r.reserveType = :reserveType group by r.services.servicesType")
    List<ReserveCountDTO> countByServicesType(@Param("reserveType") ReserveType reserveType);


}
