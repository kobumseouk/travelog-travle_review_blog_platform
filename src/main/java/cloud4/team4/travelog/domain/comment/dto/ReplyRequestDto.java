package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

@Data
public class ReplyRequestDto {

    private String content;

    private Long memberId;
}
