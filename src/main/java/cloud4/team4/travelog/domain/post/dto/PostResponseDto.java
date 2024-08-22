package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
  private Long postId;
  private String title;
  private String content;
  private Long boardId;
  private Long memberId;
  private String periodStart;
  private String periodEnd;
  private String region;
  private LocalDateTime createdAt;
  private LocalDateTime editedAt;
  private Integer views;
  private Integer recommends;

  private List<String> photos;    // 게시글에 첨부된 이미지 경로

  // private List<CommentDto> comments;
}

