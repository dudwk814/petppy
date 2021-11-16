package petppy.config.converter;

import org.springframework.core.convert.converter.Converter;
import petppy.domain.enquiry.EnquiryType;
import petppy.domain.user.Rating;

public class StringToEnquiryType implements Converter<String, EnquiryType> {

    @Override
    public EnquiryType convert(String source) {
        return EnquiryType.valueOf(source.toUpperCase());
    }
}
