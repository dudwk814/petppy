package petppy.config.converter;

import org.springframework.core.convert.converter.Converter;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;

public class StringToReserveType implements Converter<String, ReserveType> {

    @Override
    public ReserveType convert(String source) {
        return ReserveType.valueOf(source.toUpperCase());
    }

}
