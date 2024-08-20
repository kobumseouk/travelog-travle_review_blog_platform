package cloud4.team4.travelog.domain.comment.repository;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.ExPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(ExPost post);

    Comment findCommentById(Long id);
}
