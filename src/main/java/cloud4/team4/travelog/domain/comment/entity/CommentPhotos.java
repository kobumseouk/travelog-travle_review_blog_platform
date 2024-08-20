package cloud4.team4.travelog.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommentPhotos {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_photos_id")
    private Long id;

    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
