package petppy.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    // DTO 리스트
    private List<DTO> dtoList;

    // 총 페이지 개수
    private int totalPage;

    // 전체 데이터 개수
    private long totalElements;

    // 현재 페이지 번호
    private int page;

    // 목록 사이즈 (한 페이지의 몇개의 목록)
    private int size;

    // 다음, 이전 페이지 여부
    boolean next, prev;

    // 시작, 끝 페이지 번호
    private int start, end;

    // 페이지 번호 목록
    private List<Integer> pageList;


    @Builder
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn, int totalPage, int page, int size, long totalElements) {
        this.dtoList = result.stream().map(fn).collect(Collectors.toList());
        this.totalPage = totalPage;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;

        // temp end page
        int temEnd = (int)(Math.ceil(page / 10.0)) * 10;

        start = temEnd - 9;

        prev = start > 1;

        end = totalPage > temEnd ? temEnd : totalPage;

        next = totalPage > temEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }


}
