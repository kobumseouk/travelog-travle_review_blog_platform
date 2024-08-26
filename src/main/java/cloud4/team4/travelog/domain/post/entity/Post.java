package cloud4.team4.travelog.domain.post.entity;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import cloud4.team4.travelog.domain.member.entity.Member;
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

  @NotBlank
  @Size(max = 50)
  @Column(nullable = false)
  private String title;

  @NotBlank
  @Column(columnDefinition = "TEXT")
  private String content;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private Board board;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @NotNull
  @Column(name = "period_start")
  private String periodStart;

  @NotNull
  @Column(name = "period_end")
  private String periodEnd;

  @NotNull
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @NotNull
  @Column(name = "edited_at")
  private LocalDateTime editedAt;

  @PositiveOrZero
  private Integer views;

  @PositiveOrZero
  private Integer recommends;


  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private List<PostPhoto> postPhotos = new ArrayList<>();

  // 댓글 추가
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();


}
