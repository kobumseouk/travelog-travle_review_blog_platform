package cloud4.team4.travelog.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
  private String title;
  private String content;
  private String periodStart;
  private String periodEnd;
  private List<MultipartFile> photos;
  private List<String> photoPositions;

  // private Long memberId;
  // private Long boardId;  // 게시판 변경
}
