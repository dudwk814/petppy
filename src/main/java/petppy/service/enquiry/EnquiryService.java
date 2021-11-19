package petppy.service.enquiry;

import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.enquiry.EnquiryDTO;

import java.util.List;

public interface EnquiryService {

    void createEnquiry(EnquiryDTO enquiryDTO);

    EnquiryDTO findEnquiry(Long id);

    List<EnquiryDTO> findEnquiryListWithUserEmail(String email);

    public PageResultDTO<EnquiryDTO, Enquiry> findEnquiryListWithPaging(EnquiryDTO enquiryDTO, PageRequestDTO requestDTO);

    void changeEnquiry(EnquiryDTO enquiryDTO);

    void deleteEnquiry(Long id);

    void changeEnquiryStatusToComplete(Long id);

    default Enquiry dtoToEntity(EnquiryDTO enquiryDTO) {

        User user = User.builder().id(enquiryDTO.getUserId()).build();

        return Enquiry.builder()
                .user(user)
                .content(enquiryDTO.getContent())
                .enquiryType(enquiryDTO.getEnquiryType())
                .enquiryStatus(EnquiryStatus.BEFORE)
                .title(enquiryDTO.getTitle())
                .build();
    }

    default EnquiryDTO entityToDTO(Enquiry enquiry) {
        return EnquiryDTO.builder()
                .enquiryId(enquiry.getId())
                .userId(enquiry.getUser().getId())
                .userEmail(enquiry.getUser().getEmail())
                .title(enquiry.getTitle())
                .content(enquiry.getContent())
                .enquiryType(enquiry.getEnquiryType())
                .enquiryStatus(enquiry.getEnquiryStatus())
                .createdDate(enquiry.getCreatedDate())
                .updateDate(enquiry.getModifiedDate())
                .build();
    }
}
