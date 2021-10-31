package petppy.dto.user;

import lombok.Data;
import petppy.domain.user.Rating;

@Data
public class MembershipCountDTO {

    private Rating rating;

    private Long count;

    public MembershipCountDTO(Rating rating, Long count) {
        this.rating = rating;
        this.count = count;
    }
}
