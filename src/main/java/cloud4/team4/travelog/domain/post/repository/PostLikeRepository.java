package cloud4.team4.travelog.domain.post.repository;

import cloud4.team4.travelog.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
  PostLike findPostLikeByMember_IdAndPost_Id(Long memberId, Long postId);
}
