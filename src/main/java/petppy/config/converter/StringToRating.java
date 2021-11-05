package petppy.config.converter;

import org.springframework.core.convert.converter.Converter;
import petppy.domain.user.Rating;

public class StringToRating implements Converter<String, Rating> {

@Override
public Rating convert(String source) {
        return Rating.valueOf(source.toUpperCase());
        }
}
