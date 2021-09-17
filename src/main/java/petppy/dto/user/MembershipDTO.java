package petppy.dto.user;

import lombok.Builder;
import lombok.Data;
import petppy.domain.user.Rating;

import java.time.LocalDateTime;

@Data
public class MembershipDTO {

    private Long id;
    private String email;
    private String name;
    private Rating rating;
    private LocalDateTime createdDate;
    private LocalDateTime lastDate;

    @Builder
    public MembershipDTO(Long id, String email, Rating rating, String name, LocalDateTime createdDate) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.rating = rating;
        this.createdDate = createdDate;
        this.lastDate = createdDate.plusMonths(1L);
    }
}
