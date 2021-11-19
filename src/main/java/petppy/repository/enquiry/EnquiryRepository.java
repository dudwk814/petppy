package petppy.repository.enquiry;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import petppy.domain.enquiry.Enquiry;
import petppy.dto.PageRequestDTO;
import petppy.dto.enquiry.EnquiryDTO;

import java.util.List;
import java.util.Optional;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long>, EnquiryRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Enquiry> findById(Long aLong);

    List<Enquiry> findByUserEmail(String email);

}
