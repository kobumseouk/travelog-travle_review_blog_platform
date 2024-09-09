package cloud4.team4.travelog.domain.comment.repository;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(Post post);

    // 생성일 기준 정렬
    Page<Comment> findCommentsByPost(Post post, Pageable pageable);

    // 추천수 -> 생성일 기준 정렬
    @Query("SELECT c FROM Comment c LEFT JOIN c.commentLikes cl " +
            "WHERE c.post = :post " +
            "GROUP BY c.id ORDER BY COUNT(cl) DESC, c.createdAt DESC")
    Page<Comment> findCommentsSortedByLikes(@Param("post") Post post, Pageable pageable);


    Comment findCommentById(Long id);
}
