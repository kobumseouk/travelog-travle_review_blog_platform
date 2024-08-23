package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponseDto {

    private String content;

    private Long memberId;
    private Long postId;

    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    // 댓글에 첨부된 사진의 경로
    private List<String> photos;
}
