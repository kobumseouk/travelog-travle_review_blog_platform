package cloud4.team4.travelog.domain.comment.dto;

import cloud4.team4.travelog.domain.comment.entity.ExMember;
import cloud4.team4.travelog.domain.comment.entity.ExPost;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {

    private String content;

    private String memberId;

    private LocalDateTime created_at;
    private LocalDateTime edited_at;
}
