package cloud4.team4.travelog.domain.comment.repository;

import cloud4.team4.travelog.domain.comment.entity.CommentPhotos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentPhotosRepository extends JpaRepository<CommentPhotos, Long> {

    List<CommentPhotos> findCommentPhotosByComment_Id(Long commentId);
}
