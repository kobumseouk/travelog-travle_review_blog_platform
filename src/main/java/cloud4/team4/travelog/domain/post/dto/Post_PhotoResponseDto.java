package cloud4.team4.Travelog.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post_PhotoResponseDto {
  private Long post_photoId;
  private Long postId;  // Post 엔티티의 id를 직접 참조할 수 있는 필드
  private String imagePath;
}
