package cloud4.team4.travelog.domain.post.entity;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.Base64;

@Entity
@Table(name = "post_photo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPhoto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_photo_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @NotBlank
  private String imageName;

  @Lob
  @Column(columnDefinition = "MEDIUMBLOB")
  private byte[] imageData;


  public PostPhoto(String imageName, byte[] imageData, Post post) {
    this.imageName = imageName;
    this.imageData = imageData;
    this.post = post;
  }

  // Base64 문자열 추가
  public String getBase64Image() {
    return Base64.getEncoder().encodeToString(this.imageData);
  }
}
