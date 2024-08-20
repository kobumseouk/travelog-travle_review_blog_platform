package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.ExPost;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import cloud4.team4.travelog.domain.comment.repository.ExPostRepository;
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

    public List<Comment> findAll(Long postId) {

        // 메서드 시그니처 체크해야 함!
        ExPost post = exPostRepository.findExPostById(postId);

        return commentRepository.findCommentsByPost(post);
    }

//    @Transactional
//    public void saveComment(Comment comment) {
//        commentRepository.save(comment);
//    }
}
