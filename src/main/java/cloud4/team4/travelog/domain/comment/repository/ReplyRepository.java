package cloud4.team4.travelog.domain.comment.repository;

import cloud4.team4.travelog.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByComment_Id(Long commentId);

    Reply findReplyById(Long id);
}
