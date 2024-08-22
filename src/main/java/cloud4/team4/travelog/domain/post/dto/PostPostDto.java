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
public class PostPostDto {
  private String title;
  private String content;
  private Long boardId;
  private Long memberId;
  private String periodStart;
  private String periodEnd;
  private List<MultipartFile> photos;
  private List<String> photoPositions;

//  private LocalDateTime createdAt;
//  private LocalDateTime editedAt;
//  private Integer views;
//  private Integer recommends;
}
