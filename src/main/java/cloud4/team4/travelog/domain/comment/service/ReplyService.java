package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.dto.*;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.Reply;
import cloud4.team4.travelog.domain.comment.repository.ReplyRepository;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final CommentService commentService;

    /**
     * READ : 댓글 id -> 모든 대댓글 조회
     */
    public List<Reply> findAllRepliesByCommentId(Long commentId) {
        return replyRepository.findRepliesByComment_Id(commentId);
    }

    /**
     * READ : 대댓글 id -> 대댓글 단건 조회
     */
    public Reply findReplyByReplyId(Long replyId) {
        return replyRepository.findReplyById(replyId);
    }

    /**
     * CREATE : 댓글 id -> 대댓글 작성
     */
    @Transactional
    public void saveReply(Long commentId, ReplyRequestDto replyRequestDto) {

        // 아직 member와 comment를 설정하지 않음
        Reply reply = ReplyMapper.INSTANCE.toEntity(replyRequestDto);

        System.out.println("reply.getContent() = " + reply.getContent());
        System.out.println("reply.getCreatedAt() = " + reply.getCreatedAt());

        // member 설정 위해 단건 조회함
        Member member = memberRepository.findById(replyRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("member not found"));

        // post 설정 위해 단건 조회함
        Comment comment = commentService.findCommentByCommentId(commentId);

        reply.setMember(member);
        reply.setComment(comment);

        replyRepository.save(reply);
    }

    /**
     * UPDATE : 대댓글 id, dto -> 업데이트
     */
    @Transactional
    public void updateReply(Long replyId, ReplyUpdateDto replyUpdateDto) {

        Reply findReply = replyRepository.findReplyById(replyId);

        findReply.update(replyUpdateDto.getContent(), LocalDateTime.now());
    }

    /**
     * DELETE : 대댓글 id -> 삭제
     */
    @Transactional
    public void deleteReply(Long commentId) {
        replyRepository.deleteById(commentId);
    }
}
