package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListViewResponse {
  private final Long postId;
  private final String title;
  private final String memberName;
  private final LocalDateTime createdAt;
  private final Integer view;
  private final Integer recommends;


  public PostListViewResponse(Post post){
    this.postId = post.getPostId();
    this.title = post.getTitle();
    this.memberName = post.getMember().getName();
    this.createdAt = post.getCreatedAt();
    this.view = post.getViews();
    this.recommends = post.getRecommends();
  }


}
