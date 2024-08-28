package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.*;
import cloud4.team4.travelog.domain.comment.service.ReplyService;
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
                                             @RequestBody ReplyRequestDto replyRequestDto) {

        // 로그인된 멤버의 아이디 저장 => 세션 방식으로 변경해야함!
        replyRequestDto.setMemberId(loginMember());

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

    // 테스트 코드 -> 세션등에 로그인 멤버 정보 담기는 것으로 바뀌면 코드 지울것!
    @ModelAttribute("loginMember")
    public Long loginMember() {
        return 1L;
    }
}
