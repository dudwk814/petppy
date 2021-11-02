package petppy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import petppy.domain.user.Rating;

@Data
@AllArgsConstructor
@Builder
public class PageRequestDTO {

    private int page;
    private int size;

    private String type;
    private String keyword;

    private Rating rating;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }


}
