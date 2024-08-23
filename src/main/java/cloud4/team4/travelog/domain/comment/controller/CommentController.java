package cloud4.team4.travelog.domain.comment.controller;


import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public String post(@PathVariable("postId") Long postId,
                       @RequestParam(required = false, value = "commentPage", defaultValue = "1") int commentPage,
                       Model model) {

        model.addAttribute("commentRequestDto", new CommentRequestDto());
        model.addAttribute("post", postService.getPostById(postId));
        model.addAttribute("comments", commentService.findPagedCommentsByPostId(postId, commentPage));

        return "post";
    }

    @GetMapping("/post/{postId}/comment/update/{commentId}")
    public String updateCommentForm(@PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId, Model model) {
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(commentService.findCommentByCommentId(commentId).getContent());

        model.addAttribute("commentUpdateDto", commentUpdateDto);

        return "comment_update";
    }

    // 테스트 코드 -> 세션등에 로그인 멤버 정보 담기는 것으로 바뀌면 코드 지울것!
    @ModelAttribute("loginMember")
    public Long loginMember() {
        return 1L;
    }

}
