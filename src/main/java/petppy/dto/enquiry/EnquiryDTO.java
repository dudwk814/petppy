package petppy.dto.enquiry;

import lombok.Builder;
import lombok.Data;
import petppy.domain.enquiry.EnquiryType;

import java.time.LocalDateTime;

@Data
@Builder
public class EnquiryDTO {

    private Long enquiryId;

    private EnquiryType enquiryType;

    private String content;

    private String userEmail;

    private Long userId;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


}
