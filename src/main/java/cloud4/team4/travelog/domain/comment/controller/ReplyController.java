package cloud4.team4.travelog.domain.comment.controller;

import cloud4.team4.travelog.domain.comment.dto.ReplyUpdateDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.comment.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final CommentService commentService;

    // 대댓글 수정
    @GetMapping("/board/{regionMajor}/{boardId}/posts/{postId}/comments/{commentId}/replies/{replyId}")
    public String updateReplyForm(@PathVariable("regionMajor") String regionMajor,
                                  @PathVariable("boardId") Long boardId,
                                  @PathVariable("postId") Long postId,
                                  @PathVariable("commentId") Long commentId,
                                  @PathVariable("replyId") Long replyId, Model model) {

        ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto(replyService.findReplyByReplyId(replyId).getContent());

        model.addAttribute("postId", commentService.findCommentByCommentId(commentId).getPost().getId());
        model.addAttribute("replyUpdateDto", replyUpdateDto);

        return "reply_update";
    }
}
