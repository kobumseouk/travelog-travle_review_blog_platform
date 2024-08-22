package cloud4.team4.travelog.domain.post.entity;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import jakarta.persistence.Entity;
import cloud4.team4.travelog.domain.member.entity.MemberEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long postId;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private MemberEntity member;

  @Column(name = "period_start")
  private String periodStart;

  @Column(name = "period_end")
  private String periodEnd;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "edited_at")
  private LocalDateTime editedAt;

  private Integer views;

  private Integer recommends;


  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<PostPhoto> postPhoto = new ArrayList<>();

  // 댓글 추가
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();


}
