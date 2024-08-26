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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
     */
    public List<Comment> findAllByPostId(Long postId) {

        Post post = postService.getPostById(postId);
        return commentRepository.findCommentsByPost(post);

    }

    /**
     * READ
     * findPaging: 해당 게시글의 댓글 반환, 페이징 적용
     */
    public Page<Comment> findPagedCommentsByPostId(Long postId, int commentPage) {

        // 페이징 -> 댓글 5개씩 출력
        PageRequest commentPageRequest = PageRequest.of(commentPage - 1, 5, Sort.by("createdAt").descending());

        return commentRepository.findCommentsByPost(postService.getPostById(postId), commentPageRequest);

    }

    /**
     * READ
     * 댓글 단건 조회
     */
    public Comment findCommentByCommentId(Long commentId) {

        return commentRepository.findCommentById(commentId);
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

        findComment.update(commentUpdateDto.getContent(), LocalDateTime.now(), commentUpdateDto.getRating());
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
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // 게시글의 모든 댓글의 평점 평균 (소숫점 첫째자리 까지 출력)
    public double getAverageRating(Long postId) {
        Post post = postService.getPostById(postId);
        List<Comment> comments = commentRepository.findCommentsByPost(post);


        // 평균 평점 반환 -> 평점이 없다면 0.0 반환
        double average = comments.stream()
                .mapToLong(Comment::getRating)
                .average().orElse(0.0);
        return Math.round(average * 10.0) / 10.0;
    }
}
