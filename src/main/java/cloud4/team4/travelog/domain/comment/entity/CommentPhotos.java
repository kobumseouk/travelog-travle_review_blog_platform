package cloud4.team4.travelog.domain.comment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Entity
@Getter
@NoArgsConstructor
public class CommentPhotos {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_photos_id")
    private Long id;

    private String imageName;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] imageData;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentPhotos(String imageName, byte[] imageData, Comment comment) {
        this.imageName = imageName;
        this.imageData = imageData;
        this.comment = comment;
    }

    // Base64 문자열 추가
    public String getBase64Image() {
        return Base64.getEncoder().encodeToString(this.imageData);
    }
}
