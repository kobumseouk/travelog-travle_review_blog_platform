package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.CommentLike;
import cloud4.team4.travelog.domain.comment.repository.CommentLikeRepository;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void likeComment(Long memberId, Long commentId) {
        CommentLike commentLike = commentLikeRepository.findCommentLikeByMember_IdAndComment_Id(memberId, commentId);

        // 좋아요 엔티티 생성
        if(commentLike == null) {
            // member 설정 위해 단건 조회함
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new IllegalArgumentException("member not found"));

            // comment 설정 위해 단건 조회함
            Comment comment = commentRepository.findCommentById(commentId);

            CommentLike createdCommentLike = new CommentLike();
            createdCommentLike.setMember(member);
            createdCommentLike.setComment(comment);

            commentLikeRepository.save(createdCommentLike);
        } else { // 좋아요 엔티티 삭제
            commentLikeRepository.delete(commentLike);
        }
    }
}
