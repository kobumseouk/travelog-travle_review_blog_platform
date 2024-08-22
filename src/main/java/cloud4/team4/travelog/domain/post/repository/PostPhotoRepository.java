package cloud4.team4.travelog.domain.post.repository;

import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {

  List<PostPhoto> findPostPhotoByPostId(Long postId);

}

