package petppy.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {

    private Long id;

    @NotBlank(message = "답변 내용을 입력해주세요!")
    private String content;
    private Long enquiryId;


}
