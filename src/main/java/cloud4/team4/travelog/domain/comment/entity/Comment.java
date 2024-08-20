package cloud4.team4.travelog.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private ExMember member;    // 예시 멤버 엔티티 사용 -> 변경 필수!

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private ExPost post;        // 예시 게시글 엔티티 사용 -> 변경 필수!

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentPhotos> commentPhotos = new ArrayList<>();

    private String content;

    private LocalDateTime created_at;
    private LocalDateTime edited_at;

    // 예시 멤버 엔티티 사용 -> 변경 필수!
    public void setMember(ExMember member) {
        this.member = member;
        member.getCommentList().add(this);
    }

    // 예시 게시글 엔티티 사용 -> 변경 필수!
    public void setPost(ExPost post) {
        this.post = post;
        post.getCommentList().add(this);
    }

    public Comment(String content, LocalDateTime created_at) {
        this.content = content;
        this.created_at = created_at;
    }

    // comment update 메서드
    public void update(String content, LocalDateTime edited_at) {
        this.content = content;
        this.edited_at = edited_at;
    }
}
