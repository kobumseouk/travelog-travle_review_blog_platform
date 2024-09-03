package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostViewResponse {
  private Long postId;
  private String title;
  private String content;
  private String memberName;
  private String periodStart;
  private String periodEnd;
  private LocalDateTime createdAt;
  private Integer views;
  private Integer recommends;

  private List<String> photos;    // 게시글에 첨부된 이미지 경로

  public PostViewResponse(Post post) {
    this.postId = post.getPostId();
    this.title = post.getTitle();
    this.content = post.getContent();
    this.memberName = post.getMember().getName();
    this.periodStart = post.getPeriodStart();
    this.periodEnd = post.getPeriodEnd();
    this.createdAt = post.getCreatedAt();
    this.views = post.getViews();
    this.recommends = post.getRecommends();
    this.photos = post.getPostPhoto().stream()
        .map(PostPhoto::getImagePath)
        .collect(Collectors.toList());
  }
}
