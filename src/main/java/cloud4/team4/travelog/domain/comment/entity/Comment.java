package cloud4.team4.travelog.domain.comment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private ExMember member;    // 예시 멤버 엔티티 사용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private ExPost post;        // 예시 게시글 엔티티 사용

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentPhotos> commentPhotos = new ArrayList<>();

    private String content;

    private LocalDateTime created_at;
    private LocalDateTime edited_at;

    public void setMember(ExMember member) {
        this.member = member;
        member.getCommentList().add(this);
    }

    public void setPost(ExPost post) {
        this.post = post;
        post.getCommentList().add(this);
    }

    public Comment(String content, LocalDateTime created_at) {
        this.content = content;
        this.created_at = created_at;
    }
}
