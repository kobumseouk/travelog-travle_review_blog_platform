package cloud4.team4.travelog.domain.comment.repository;

import cloud4.team4.travelog.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findCommentLikeByMember_IdAndComment_Id(Long memberId, Long commentId);
}
