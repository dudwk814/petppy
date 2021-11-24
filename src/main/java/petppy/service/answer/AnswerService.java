package petppy.service.answer;

import petppy.domain.answer.Answer;
import petppy.domain.enquiry.Enquiry;
import petppy.dto.answer.AnswerDTO;

public interface AnswerService {

    void createAnswer(AnswerDTO answerDTO);

    void modifyAnswer(AnswerDTO answerDTO);

    AnswerDTO findAnswer(Long id);

    default Answer dtoToEntity(AnswerDTO answerDTO) {

        Enquiry enquiry = Enquiry.builder().id(answerDTO.getEnquiryId()).build();

        return Answer
                .builder()
                .content(answerDTO.getContent())
                .enquiry(enquiry)
                .build();
    }

    default AnswerDTO entityToDTO(Answer answer) {
        return AnswerDTO
                .builder()
                .id(answer.getId())
                .enquiryId(answer.getEnquiry().getId())
                .content(answer.getContent())
                .build();
    }


}
