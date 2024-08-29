package cloud4.team4.travelog.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReplyRequestDto {

    private String content;

    @NotNull
    private Long memberId;
}
