package petppy.dto.enquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import petppy.domain.enquiry.EnquiryType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnquiryCountDTO {

    private Long count;
    private EnquiryType enquiryType;

}
