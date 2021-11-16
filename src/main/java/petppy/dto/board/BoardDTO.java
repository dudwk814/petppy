package petppy.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private Long boardId;

    @NotBlank(message = "제목은 필수 입력 항목입니다.")
    private String title;
    private String content;
    private Integer hit;
    private Integer commentCount ;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private String email;
    private Long userId;


    @Builder
    @QueryProjection
    public BoardDTO(Long boardId, Long userId, String title, String content, Integer hit, Integer commentCount, String email, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.boardId = boardId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.hit = hit;
        this.commentCount = commentCount;
        this.email = email;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
