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
    private LocalDateTime modifiedDate;
    private LocalDateTime lastDate;

    // 서비스 이용가능 횟수
    private int dogWalkCount = 0;
    private int petGroomingCount = 0;
    private int vetVisit = 0;

    @Builder
    public MembershipDTO(Long id, String email, Rating rating, String name, LocalDateTime modifiedDate, LocalDateTime createdDate, int dogWalkCount, int petGroomingCount, int vetVisit) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.lastDate = modifiedDate.plusMonths(1L);
        this.dogWalkCount = dogWalkCount;
        this.petGroomingCount = petGroomingCount;
        this.vetVisit = vetVisit;
    }
}
