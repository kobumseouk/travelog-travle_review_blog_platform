package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.*;
import cloud4.team4.travelog.domain.comment.service.ReplyService;
import cloud4.team4.travelog.domain.member.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyApiController {

    private final ReplyService replyService;

    // READ
    @GetMapping("/comment/{commentId}/reply")
    public ResponseEntity<List<ReplyResponseDto>> findAllReplies(@PathVariable("commentId") Long commentId) {

        List<ReplyResponseDto> result = replyService.findAllRepliesByCommentId(commentId)
                .stream()
                .map(ReplyMapper.INSTANCE::toResponseDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    // CREATE
    @PostMapping("/comment/{commentId}/reply")
    public ResponseEntity<String> addReply(@PathVariable("commentId") Long commentId,
                                             @RequestBody ReplyRequestDto replyRequestDto, HttpSession session) {

        // 로그인 하지 않은 경우
        if(loginMemberId(session) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("대댓글 작성은 로그인 후 가능합니다!");
        }

        // 로그인된 멤버의 아이디 저장
        replyRequestDto.setMemberId(loginMemberId(session));

        // 저장
        try {
            replyService.saveReply(commentId, replyRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("reply created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/reply/{replyId}")
    public ResponseEntity<String> updateReply(@PathVariable("replyId") Long replyId,
                                                @ModelAttribute ReplyUpdateDto replyUpdateDto) {
        try {
            replyService.updateReply(replyId, replyUpdateDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("reply updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyId") Long replyId) {

        replyService.deleteReply(replyId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ModelAttribute("loginMember")
    public Long loginMemberId(HttpSession session) {
        // 세션에서 로그인한 멤버의 id 값 가져옴
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        if(memberDto == null) return null;
        return memberDto.getId();
    }
}
