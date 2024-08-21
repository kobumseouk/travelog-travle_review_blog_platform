package cloud4.team4.travelog.domain.comment.entity;

import jakarta.persistence.*;
import cloud4.team4.travelog.domain.member.entity.MemberEntity;
import cloud4.team4.travelog.domain.post.entity.Post;
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

    // comment와 member 는 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;
//    private ExMember member;      // 테스트 용 코드

    // comment와 post는 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
//    private ExPost post;          // 테스트 용 코드

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentPhotos> commentPhotos = new ArrayList<>();

    private String content;

    private LocalDateTime created_at;
    private LocalDateTime edited_at;

    // 테스트 용 주석처리
    public void setMember(MemberEntity member) {
        this.member = member;
        // ArrayList 이름 확인 필수
        member.getComments().add(this);
    }

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    // 테스트 용 코드
//    public void setMember(ExMember member) {
//        this.member = member;
//        member.getComments().add(this);
//    }
//    public void setPost(ExPost post) {
//        this.post = post;
//        post.getComments().add(this);
//    }

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
