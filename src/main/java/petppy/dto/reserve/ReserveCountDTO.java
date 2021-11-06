package petppy.dto.reserve;

import lombok.Data;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.ServicesType;

@Data
public class ReserveCountDTO {

    private Long count;
    private ServicesType servicesType;

    public ReserveCountDTO(Long count, ServicesType servicesType) {
        this.count = count;
        this.servicesType = servicesType;
    }
}
