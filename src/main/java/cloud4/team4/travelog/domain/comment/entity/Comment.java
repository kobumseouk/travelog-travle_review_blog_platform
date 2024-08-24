package cloud4.team4.travelog.domain.comment.entity;

import jakarta.persistence.*;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // comment와 member 는 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // comment와 post는 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentPhotos> commentPhotos = new ArrayList<>();

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    // 평점 (1 ~ 5점 선택 가능)
    private Long rating;

    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public Comment(String content, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;
    }

    // comment update 메서드
    public void update(String content, LocalDateTime editedAt, Long rating) {
        this.content = content;
        this.editedAt = editedAt;
        this.rating = rating;
    }
}
