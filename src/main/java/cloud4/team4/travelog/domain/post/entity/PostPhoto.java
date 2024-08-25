package cloud4.team4.travelog.domain.post.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

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
  private Long post_photoId;

  @NotNull
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @NotNull
  @NotBlank
  @Column(name = "image_path")
  private String imagePath;

  @NotNull
  @NotBlank
  @Column(name = "position")   // 위치 정보 추가
  private String position;


  public PostPhoto(Post post, String imagePath, String position) {
    this.post = post;
    this.imagePath = imagePath;
    this.position = position;
  }

}
