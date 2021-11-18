package petppy.service.enquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.enquiry.Enquiry;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.enquiry.EnquiryDTO;
import petppy.exception.EnquiryNotFoundException;
import petppy.repository.enquiry.EnquiryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnquiryServiceImpl implements EnquiryService{
    
    private final EnquiryRepository enquiryRepository;


    @Override
    public PageResultDTO<EnquiryDTO, Enquiry> findEnquiryList(EnquiryDTO enquiryDTO, PageRequestDTO requestDTO) {
        return null;
    }

    @Transactional
    @Override
    public void createEnquiry(EnquiryDTO enquiryDTO) {
        enquiryRepository.save(dtoToEntity(enquiryDTO));
    }

    @Override
    public EnquiryDTO findEnquiry(Long id) {
        return entityToDTO(enquiryRepository.findById(id).orElseThrow(EnquiryNotFoundException::new));
    }

    @Override
    public List<EnquiryDTO> findEnquiryListWithUserEmail(String email) {

        List<Enquiry> result = enquiryRepository.findByUserEmail(email);

        return result.stream().map(enquiry -> entityToDTO(enquiry)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void changeEnquiry(EnquiryDTO enquiryDTO) {
        Enquiry enquiry = enquiryRepository.findById(enquiryDTO.getEnquiryId()).orElseThrow(EnquiryNotFoundException::new);
        enquiry.changeContent(enquiryDTO.getContent());
    }

    @Transactional
    @Override
    public void deleteEnquiry(Long id) {
        Enquiry enquiry = enquiryRepository.findById(id).orElseThrow(EnquiryNotFoundException::new);
        enquiryRepository.delete(enquiry);
    }

    @Override
    public void changeEnquiryStatusToComplete(Long id) {
        Enquiry enquiry = enquiryRepository.findById(id).orElseThrow(EnquiryNotFoundException::new);
        enquiry.changeEnquiryStatusToComplete();

    }
}
