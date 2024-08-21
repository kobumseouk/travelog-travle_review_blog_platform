package cloud4.team4.travelog.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostDto {
  private String title;
  private String content;
  private Long boardId;
  private Long memberId;
  private LocalDateTime periodStart;
  private LocalDateTime periodEnd;
  private String region;
//  private LocalDateTime createdAt;
//  private LocalDateTime editedAt;
//  private Integer views;
//  private Integer recommends;
}
