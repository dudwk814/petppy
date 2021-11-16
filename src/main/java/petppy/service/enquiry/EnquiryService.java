package petppy.service.enquiry;

import petppy.domain.enquiry.Enquiry;
import petppy.domain.user.User;
import petppy.dto.enquiry.EnquiryDTO;

import java.util.List;

public interface EnquiryService {

    void createEnquiry(EnquiryDTO enquiryDTO);

    EnquiryDTO findEnquiry(Long id);

    List<EnquiryDTO> findEnquiryListWithUserEmail(String email);

    void changeEnquiry(EnquiryDTO enquiryDTO);

    void deleteEnquiry(Long id);

    default Enquiry dtoToEntity(EnquiryDTO enquiryDTO) {

        User user = User.builder().id(enquiryDTO.getUserId()).build();

        return Enquiry.builder()
                .user(user)
                .content(enquiryDTO.getContent())
                .enquiryType(enquiryDTO.getEnquiryType())
                .build();
    }

    default EnquiryDTO entityToDTO(Enquiry enquiry) {
        return EnquiryDTO.builder()
                .enquiryId(enquiry.getId())
                .userId(enquiry.getUser().getId())
                .userEmail(enquiry.getUser().getEmail())
                .content(enquiry.getContent())
                .enquiryType(enquiry.getEnquiryType())
                .createdDate(enquiry.getCreatedDate())
                .updateDate(enquiry.getModifiedDate())
                .build();
    }
}
