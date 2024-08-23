package cloud4.team4.travelog.domain.comment.controller;


import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;

    @GetMapping("/post/{postId}")
    public String post(@PathVariable("postId") Long postId, Model model) {

        model.addAttribute("commentRequestDto", new CommentRequestDto());
        model.addAttribute("post", postService.getPostById(postId));

        // post만 넘기면 아래는 없어도 됨
//        model.addAttribute("commentList", commentService.findAllByPostId(postId));

        return "post";
    }

    @GetMapping("/mainV")
    public String mainV() {
        return "main";
    }
}
