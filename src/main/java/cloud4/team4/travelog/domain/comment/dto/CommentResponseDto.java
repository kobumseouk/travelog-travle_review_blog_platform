package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {

    private String content;

    private String memberId;
    private Long postId;

    private LocalDateTime created_at;
    private LocalDateTime edited_at;
}
