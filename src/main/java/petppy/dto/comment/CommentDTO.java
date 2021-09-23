package petppy.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import petppy.domain.comment.DeleteStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO implements Serializable {

    private Long id;
    private String content;
    private Long boardId;
    private Long userId;
    private Long parentId;
    private String email;
    private int childrenCount;
    private DeleteStatus deleteStatus;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    @Builder.Default
    private List<CommentDTO> children = new ArrayList<>();


    public CommentDTO(Long parentId, Long id, String content, Long boardId, Long userId, String email, int childrenCount, LocalDateTime createdDate, LocalDateTime lastModifiedDate, DeleteStatus deleteStatus) {
        this.id = id;
        this.content = content;
        this.boardId = boardId;
        this.userId = userId;
        this.parentId = parentId;
        this.email = email;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.childrenCount = childrenCount;
        this.deleteStatus = deleteStatus;
    }

}
