package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.dto.CommentMapper;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.service.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;
    private final PostService postService;

    private final CommentPhotosService commentPhotosService;

    /**
     * READ
     * findAll: 해당 게시글의 모든 댓글 반환
     *
     *
     */
    public List<Comment> findAllByPostId(Long postId) {

        Post post = postService.getPostById(postId);
        return commentRepository.findCommentsByPost(post);

    }

    /**
     * CREATE
     * save: comment 객체 엔티티화
     */
    @Transactional
    // 파라미터 통일 필요, 예시 엔티티 사용 -> 변경 필수!
    public void saveComment(Long postId, CommentRequestDto commentRequestDto) {

        // 아직 member와 post를 설정하지 않음
        Comment comment = CommentMapper.INSTANCE.toEntity(commentRequestDto);

        // member 설정 위해 단건 조회함
        Member member = memberRepository.findById(commentRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("member not found"));

        // post 설정 위해 단건 조회함
        Post post = postService.getPostById(postId);

        comment.setMember(member);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        try {
            commentPhotosService.savePhotos(commentRequestDto.getPhotos(), savedComment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * UPDATE
     * updateComment: comment 찾고 내부 메서드 update 호출
     */
    @Transactional
    public void updateComment(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comment findComment = commentRepository.findCommentById(commentId);

        findComment.update(commentUpdateDto.getContent(), LocalDateTime.now());
        try {
            commentPhotosService.updatePhotos(commentUpdateDto.getPhotos(), findComment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * DELETE
     * deleteComment:
     */
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
