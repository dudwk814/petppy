package petppy.repository.enquiry;

import org.springframework.data.domain.Page;
import petppy.domain.enquiry.Enquiry;
import petppy.dto.PageRequestDTO;
import petppy.dto.enquiry.EnquiryDTO;

public interface EnquiryRepositoryCustom {

    Page<Enquiry> findEnquiryListWithPaging(EnquiryDTO enquiryDTO, PageRequestDTO pageRequestDTO);
}
