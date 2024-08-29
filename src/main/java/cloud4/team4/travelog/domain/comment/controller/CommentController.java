package cloud4.team4.travelog.domain.comment.controller;


import cloud4.team4.travelog.domain.comment.dto.CommentPagingInfo;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.service.CommentLikeService;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
    private final CommentLikeService commentLikeService;

    // PostViewController로 통합
    /*@GetMapping("/post/{postId}")
    public String post(@PathVariable("postId") Long postId,
                       @RequestParam(required = false, value = "commentPage", defaultValue = "1") int commentPage,
                       @ModelAttribute("commentPagingInfo")CommentPagingInfo commentPagingInfo,
                       Model model) {

        model.addAttribute("commentRequestDto", new CommentRequestDto());
        model.addAttribute("post", postService.getPostById(postId));
        model.addAttribute("comments", commentService.findPagedCommentsByPostId(postId, commentPage, commentPagingInfo));
        model.addAttribute("averageRating", commentService.getAverageRating(postId));

        return "post";
    }*/

    // 댓글 수정
    @GetMapping("/boards/{regionMajor}/posts/{postId}/comments/{commentId}")
    public String updateCommentForm(@PathVariable("regionMajor") String regionMajor,
                                    @PathVariable("postId") Long postId,
                                    @PathVariable("commentId") Long commentId, Model model) {
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(commentService.findCommentByCommentId(commentId).getContent(),
                commentService.findCommentByCommentId(commentId).getRating());

        model.addAttribute("boardCategory", postService.getPostById(postId).getBoard().getBoardCategory());
        model.addAttribute("commentUpdateDto", commentUpdateDto);

        return "comment_update";
    }

    // 댓글 좋아요
    @GetMapping("/boards/{regionMajor}/posts/{postId}/comments/{commentId}/like")
    public String likeComment(@PathVariable("regionMajor") String regionMajor,
                              @PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId, Model model) {

        // 로그인 한 멤버의 id
        Long memberId = (Long) model.getAttribute("loginMember");
        commentLikeService.likeComment(memberId, commentId);

        return "redirect:/boards/" + regionMajor + "/posts/" + postId;
    }

    @ModelAttribute("loginMember")
    public Long loginMemberId(HttpSession session) {
        // 세션에서 로그인한 멤버의 id 값 가져옴
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        if(memberDto == null) return null;
        return memberDto.getId();
    }

}
