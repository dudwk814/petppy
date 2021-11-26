package petppy.service.enquiry;

import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.answer.AnswerDTO;
import petppy.dto.enquiry.EnquiryCountDTO;
import petppy.dto.enquiry.EnquiryDTO;

import java.util.List;

public interface EnquiryService {

    void createEnquiry(EnquiryDTO enquiryDTO);

    EnquiryDTO findEnquiry(Long id);

    List<EnquiryDTO> findEnquiryListWithUserEmail(String email);

    List<EnquiryCountDTO> countByEnquiryWithEnquiryType(EnquiryStatus enquiryStatus);

    PageResultDTO<EnquiryDTO, Enquiry> findEnquiryListWithPaging(EnquiryDTO enquiryDTO, PageRequestDTO requestDTO);

    PageResultDTO<EnquiryDTO, Enquiry> searchEnquiryList(EnquiryDTO enquiryDTO, PageRequestDTO requestDTO);

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

        if (enquiry.getAnswer() == null) {
            return EnquiryDTO.builder()
                    .enquiryId(enquiry.getId())
                    .userId(enquiry.getUser().getId())
                    .userEmail(enquiry.getUser().getEmail())
                    .userName(enquiry.getUser().getName())
                    .title(enquiry.getTitle())
                    .content(enquiry.getContent())
                    .enquiryType(enquiry.getEnquiryType())
                    .enquiryStatus(enquiry.getEnquiryStatus())
                    .createdDate(enquiry.getCreatedDate())
                    .updateDate(enquiry.getModifiedDate())
                    .build();
        } else {
            AnswerDTO answerDTO = AnswerDTO.builder().content(enquiry.getAnswer().getContent()).id(enquiry.getAnswer().getId()).build();

            return EnquiryDTO.builder()
                    .enquiryId(enquiry.getId())
                    .userId(enquiry.getUser().getId())
                    .userEmail(enquiry.getUser().getEmail())
                    .userName(enquiry.getUser().getName())
                    .title(enquiry.getTitle())
                    .content(enquiry.getContent())
                    .enquiryType(enquiry.getEnquiryType())
                    .enquiryStatus(enquiry.getEnquiryStatus())
                    .createdDate(enquiry.getCreatedDate())
                    .updateDate(enquiry.getModifiedDate())
                    .answerDTO(answerDTO)
                    .build();
        }

    }
}
