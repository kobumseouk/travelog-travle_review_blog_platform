package cloud4.team4.travelog.domain.post.dto;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {

  private Long id;
  private Long boardId;  // 게시판 변경
  private Long memberId;

  @Size(min = 1, max = 50, message = "제목은 1자 이상 50자 이하여야 합니다.")
  private String title;

  private String content;

  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "시작 날짜는 YYYY-MM-DD 형식이어야 합니다.")
  private String periodStart;

  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "종료 날짜는 YYYY-MM-DD 형식이어야 합니다.")
  private String periodEnd;

  private List<MultipartFile> photos;

}
