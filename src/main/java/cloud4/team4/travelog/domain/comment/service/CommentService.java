package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 체크해야 함!
//    private final PostService postService;

    /**
     * READ
     * findAll: 해당 게시글의 모든 댓글 반환
     */
    public List<Comment> findAllByPostId(Long postId) {

        // 메서드 시그니처 체크해야 함!
        Post post = postRepository.findPostByPostId(postId);

        return commentRepository.findCommentsByPost(post);
    }

    /**
     * CREATE
     * save: comment 객체 엔티티화
     */
    @Transactional
    // 파라미터 통일 필요, 예시 엔티티 사용 -> 변경 필수!
    public void saveComment(MemberEntity member, Post post, Comment comment) {
        comment.setMember(member);
        comment.setPost(post);

        commentRepository.save(comment);
    }


    /**
     * UPDATE
     * updateComment: comment 찾고 내부 메서드 update 호출
     */
    @Transactional
    public Comment updateComment(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment findComment = commentRepository.findCommentById(commentId);

        findComment.update(commentUpdateDto.getContent(), LocalDateTime.now());

        return findComment;
    }

    /**
     * DELETE
     * deleteComment:
     */
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
