package cloud4.team4.Travelog.Post.Entity;

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
public class Post_Photo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_photo_id")
  private Long post_photoId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @Column(name = "image_path")
  private String imagePath;

}
