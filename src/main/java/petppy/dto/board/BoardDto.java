package petppy.dto.board;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDto {

    private Long boardId;
    private String title;
    private String content;
    private int hit;
    private int commentCount;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private String email;
    private Long userId;


    @Builder
    @QueryProjection
    public BoardDto(Long boardId, Long userId, String title, String content, int hit, int commentCount, String email, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
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
