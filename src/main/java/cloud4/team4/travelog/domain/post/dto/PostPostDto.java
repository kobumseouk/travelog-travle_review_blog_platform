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
public class PostPostDto {

  @NotBlank(message = "필수 입력 항목: 제목")
  @Size(min = 1, max = 50, message = "제목은 1자 이상 50자 이하여야 합니다.")
  private String title;

  @NotBlank(message = "필수 입력 항목: 내용")
  private String content;

  @NotNull(message = "필수 입력 항목: 게시판 ID")
  private Long boardId;

  @NotNull(message = "필수 입력 항목: 회원 ID")
  private Long memberId;

  @NotBlank(message = "필수 입력 항목: 시작 날짜")
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "시작 날짜는 YYYY-MM-DD 형식이어야 합니다.")
  private String periodStart;

  @NotBlank(message = "필수 입력 항목: 종료 날짜")
  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "종료 날짜는 YYYY-MM-DD 형식이어야 합니다.")
  private String periodEnd;

  @Size(max = 1, message = "사진은 최대 1개까지 업로드할 수 있습니다.")
  private List<MultipartFile> photos;

  @Size(max = 1, message = "사진 위치 정보는 최대 1개까지 입력할 수 있습니다.")
  private List<String> photoPositions;

  // photos와 photoPositions의 크기가 일치하는지 확인하는 커스텀 유효성 검사 메서드
  @AssertTrue(message = "조건: 사진 개수와 위치 정보 개수 일치")
  private boolean isPhotoAndPositionCountMatch() {
    return photos != null && photoPositions != null && photos.size() == photoPositions.size();
  }


//  private LocalDateTime createdAt;
//  private LocalDateTime editedAt;
//  private Integer views;
//  private Integer recommends;
}
