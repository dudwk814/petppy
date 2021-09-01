package petppy.repository.querydsl;

import org.springframework.data.domain.Page;
import petppy.domain.Item;
import petppy.dto.PageRequestDTO;

public interface ItemRepositoryCustom {

    Page<Item> searchItemList(PageRequestDTO requestDTO);
}
