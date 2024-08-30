package cloud4.team4.travelog.domain.comment.entity;

import cloud4.team4.travelog.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    // comment와 member 는 다대일 관계 => 단방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getReplies().add(this);
    }

    public void update(String content, LocalDateTime editedAt) {
        this.content = content;
        this.editedAt = editedAt;
    }
}
