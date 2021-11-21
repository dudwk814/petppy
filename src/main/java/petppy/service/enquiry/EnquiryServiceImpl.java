package petppy.service.enquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.enquiry.Enquiry;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.enquiry.EnquiryCountDTO;
import petppy.dto.enquiry.EnquiryDTO;
import petppy.exception.EnquiryNotFoundException;
import petppy.repository.enquiry.EnquiryRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EnquiryServiceImpl implements EnquiryService{
    
    private final EnquiryRepository enquiryRepository;


    @Override
    public PageResultDTO<EnquiryDTO, Enquiry> findEnquiryListWithPaging(EnquiryDTO enquiryDTO, PageRequestDTO requestDTO) {

        Page<Enquiry> result = enquiryRepository.findEnquiryListWithPaging(enquiryDTO, requestDTO);

        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();

        Function<Enquiry, EnquiryDTO> fn = (enquiry -> entityToDTO(enquiry));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);
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

    @Override
    public List<EnquiryCountDTO> countByEnquiryWithEnquiryType(EnquiryStatus enquiryStatus) {
        return enquiryRepository.countByEnquiryWithEnquiryType(enquiryStatus);
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
