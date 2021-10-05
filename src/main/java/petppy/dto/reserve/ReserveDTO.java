package petppy.dto.reserve;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveDTO {

    private Long id;

    private Long userId;

    private String email;

    private Long servicesId;

    private ServicesType servicesType;

    private String reserveStartDate;

    private String reserveEndDate;

    private ReserveType reserveType;

}
