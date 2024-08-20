package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestDto {

    private String content;

    private String memberId;

    private LocalDateTime created_at;

}
