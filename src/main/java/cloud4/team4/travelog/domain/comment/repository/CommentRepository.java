package cloud4.team4.travelog.domain.comment.repository;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(Post post);
    // 테스트 용
//    List<Comment> findCommentsByPost(ExPost exPost);

    Comment findCommentById(Long id);
}
