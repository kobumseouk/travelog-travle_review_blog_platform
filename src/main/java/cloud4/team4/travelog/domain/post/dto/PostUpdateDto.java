package cloud4.team4.travelog.domain.post.dto;

import jakarta.validation.constraints.*;

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

  @Size(min = 1, max = 50, message = "제목은 1자 이상 50자 이하여야 합니다.")
  private String title;

  private String content;

  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "시작 날짜는 YYYY-MM-DD 형식이어야 합니다.")
  private String periodStart;

  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "종료 날짜는 YYYY-MM-DD 형식이어야 합니다.")
  private String periodEnd;

  // @Size(max = 1, message = "사진은 최대 1개까지 업로드할 수 있습니다.")
  private MultipartFile photo;

  // @Size(max = 1, message = "사진 위치 정보는 최대 1개까지 업로드할 수 있습니다.")
  private String photoPosition;

  // private Long memberId;
  // private Long boardId;  // 게시판 변경
}
