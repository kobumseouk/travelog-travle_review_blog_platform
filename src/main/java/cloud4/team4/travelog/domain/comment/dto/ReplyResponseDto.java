package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyResponseDto {

    private Long id;

    private String content;

    private Long memberId;
    private String nickname;

    private LocalDateTime createdAt;
    private LocalDateTime editedAt;
}
