package cloud4.team4.travelog.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPhotoDto {   // PostResponseDto에서 사용
  private Long postPhotoId;
  private String imageName;
  private String base64Image;
  private String position;
}
