package petppy.config.converter;

import org.springframework.core.convert.converter.Converter;
import petppy.domain.services.ServicesType;

public class StringToServicesType implements Converter<String, ServicesType> {

    @Override
    public ServicesType convert(String source) {
        return ServicesType.valueOf(source.toUpperCase());
    }
}
