package petppy.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    @Builder.Default
    private List<CommentDTO> children = new ArrayList<>();


    public CommentDTO(Long parentId, Long id, String content, Long boardId, Long userId, String email) {
        this.id = id;
        this.content = content;
        this.boardId = boardId;
        this.userId = userId;
        this.parentId = parentId;
        this.email = email;
    }

}
