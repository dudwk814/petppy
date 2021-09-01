package petppy.repository.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import petppy.domain.Board;
import petppy.domain.Item;
import petppy.domain.QItem;
import petppy.dto.PageRequestDTO;

import javax.persistence.EntityManager;

import java.util.List;

import static petppy.domain.QItem.item;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> searchItemList(PageRequestDTO requestDTO) {

        QueryResults<Item> results = queryFactory
                .select(item)
                .from(item)
                .where(
                        priceGoe(requestDTO.getPriceGoe()),
                        priceLoe(requestDTO.getPriceLoe()),
                        nameContains(requestDTO.getKeyword())
                )
                .offset(requestDTO.getPage() - 1)
                .limit(requestDTO.getSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, requestDTO.getPageable(Sort.by("id").ascending()), total);


    }

    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe == null ? null : item.price.goe(priceGoe);
    }

    private BooleanExpression priceLoe(Integer priceLoe) {
        return priceLoe == null ? null : item.price.loe(priceLoe);
    }

    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? item.name.contains(name) : null;
    }


}
