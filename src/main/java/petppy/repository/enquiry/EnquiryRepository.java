package petppy.repository.enquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.dto.PageRequestDTO;
import petppy.dto.enquiry.EnquiryCountDTO;
import petppy.dto.enquiry.EnquiryDTO;

import java.util.List;
import java.util.Optional;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long>, EnquiryRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Enquiry> findById(Long aLong);

    List<Enquiry> findByUserEmail(String email);

    @Query("select new petppy.dto.enquiry.EnquiryCountDTO(count(e), e.enquiryType) from Enquiry e where e.enquiryStatus = :enquiryStatus group by e.enquiryType")
    List<EnquiryCountDTO> countByEnquiryWithEnquiryType(@Param("enquiryStatus") EnquiryStatus enquiryStatus);

}
