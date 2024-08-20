package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestDto {

    private String content;

//    private Member member;
//    private

    private LocalDateTime created_at;
    private LocalDateTime edited_at;
}
