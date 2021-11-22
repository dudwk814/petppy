package petppy.dto.enquiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import petppy.domain.enquiry.EnquiryStatus;
import petppy.domain.enquiry.EnquiryType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnquiryDTO {

    private Long enquiryId;

    private EnquiryType enquiryType;
    private EnquiryStatus enquiryStatus;

    @NotBlank(message = "문의 내용을 입력해주세요!")
    private String content;

    @NotBlank(message = "문의 제목을 입력해주세요!")
    private String title;

    private String userEmail;

    private String userName;

    private Long userId;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


}
