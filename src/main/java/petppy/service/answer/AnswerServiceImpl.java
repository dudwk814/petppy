package petppy.service.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.answer.Answer;
import petppy.domain.enquiry.Enquiry;
import petppy.dto.answer.AnswerDTO;
import petppy.exception.AnswerNotFoundException;
import petppy.exception.EnquiryNotFoundException;
import petppy.repository.answer.AnswerRepository;
import petppy.repository.enquiry.EnquiryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final EnquiryRepository enquiryRepository;

    @Transactional
    @Override
    public void createAnswer(AnswerDTO answerDTO) {
        Enquiry enquiry = enquiryRepository.findById(answerDTO.getEnquiryId()).orElseThrow(EnquiryNotFoundException::new);

        answerRepository.save(dtoToEntity(answerDTO));

        enquiry.changeEnquiryStatusToComplete();
    }

    @Transactional
    @Override
    public void modifyAnswer(AnswerDTO answerDTO) {
        Answer answer = answerRepository.findById(answerDTO.getId()).orElseThrow(AnswerNotFoundException::new);
        answer.updateAnswer(answerDTO.getContent());
    }

    @Override
    public AnswerDTO findAnswer(Long id) {
        return entityToDTO(answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new));
    }




}
