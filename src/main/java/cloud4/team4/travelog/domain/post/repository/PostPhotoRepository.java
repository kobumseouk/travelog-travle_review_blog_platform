package cloud4.team4.travelog.domain.post.repository;

import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {
  void deleteByPost(Post post);
  List<PostPhoto> findAllPhotosByPost_Id(Long postId);
}

