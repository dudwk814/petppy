package petppy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class CommentDTO implements Serializable {

    private String content;
    private Long boardId;
    private Long userId;
    private Long parentId;
    private String email;

    @Builder
    public CommentDTO(String content, Long ticketId, Long userId, Long parentId, String email) {
        this.content = content;
        this.boardId = ticketId;
        this.userId = userId;
        this.parentId = parentId;
        this.email = email;
    }
}
