package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListViewResponse {
  private final Long postId;
  private final String title;
  // private final String content;
  private final String memberName;
  private final LocalDateTime createdAt;
  private final Integer views;
  private final Integer recommends;


  public PostListViewResponse(Post post){
    this.postId = post.getId();
    this.title = post.getTitle();
    // this.content = post.getContent();
    this.memberName = post.getMember().getName();
    this.createdAt = post.getCreatedAt();
    this.views = post.getViews();
    this.recommends = post.getRecommends();
  }


}
