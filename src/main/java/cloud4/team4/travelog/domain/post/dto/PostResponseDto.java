package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import jakarta.validation.constraints.*;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
  @NotNull
  private Long id;
  @NotBlank
  private String title;
  @NotBlank
  private String content;

  private Long boardId;
  private Long memberId;
  private String memberName;

  private String periodStart;
  private String periodEnd;
  private LocalDateTime createdAt;
  private LocalDateTime editedAt;
  private Integer views;
  private Integer recommends;

  /*private String photoPath;    // 게시글에 첨부된 이미지 경로
  private String photoPosition;   // 이미지를 삽입할 위치*/
  private List<String> photos;

  private List<Comment> comments;

}

