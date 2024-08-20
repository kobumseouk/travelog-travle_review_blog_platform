package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.ExMember;
import cloud4.team4.travelog.domain.comment.entity.ExPost;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import cloud4.team4.travelog.domain.comment.repository.ExPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ExPostRepository exPostRepository;

    // 체크해야 함!
//    private final PostService postService;

    /**
     * READ
     * findAll: 해당 게시글의 모든 댓글 반환
     */
    public List<Comment> findAllByPostId(Long postId) {

        // 메서드 시그니처 체크해야 함!
        ExPost post = exPostRepository.findExPostById(postId);

        return commentRepository.findCommentsByPost(post);
    }

    /**
     * CREATE
     * save: comment 객체 엔티티화
     */
    @Transactional
    // 파라미터 통일 필요, 예시 엔티티 사용 -> 변경 필수!
    public void saveComment(ExMember member, ExPost post, Comment comment) {
        comment.setMember(member);
        comment.setPost(post);

        commentRepository.save(comment);
    }

}
