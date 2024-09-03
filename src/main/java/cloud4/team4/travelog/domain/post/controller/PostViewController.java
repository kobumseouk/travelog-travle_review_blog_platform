package cloud4.team4.travelog.domain.post.controller;

import cloud4.team4.travelog.domain.board.service.BoardService;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.dto.PostListViewResponse;
import cloud4.team4.travelog.domain.post.dto.PostResponseDto;
import cloud4.team4.travelog.domain.post.dto.PostViewResponse;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostViewController {
  private final BoardService boardService;
  private final PostService postService;
  private final CommentService commentService;

  @GetMapping("/board/post") // 상세 게시판
  public String post(Model model) {
    List<PostListViewResponse> posts = postService.getAllPosts()
        .stream()
        .map(PostListViewResponse::new)
        .toList();

    model.addAttribute("Posts", posts);

    return "boardPost";
  }

  @GetMapping("board/post/{postId}")
  public String post(@PathVariable("postId") Long postId, Model model) {
    Post post = postService.getPostById(postId);

    model.addAttribute("commentRequestDto", new CommentRequestDto());
    model.addAttribute("post", new PostViewResponse(post));

    return "post";
  }

  @GetMapping("/new-post")
  public String newPost(@RequestParam(required = false, name = "postId") Long postId, Model model) {
    // id 가 없으면 새롭게 블로그를 만든다.
    // id 가 있으면 수정
    if (postId == null) {
      model.addAttribute("post", new PostViewResponse());
    } else {
      Post post = postService.getPostById(postId); // 조회하고 DTO객체에 감싸서 수정
      model.addAttribute("post", new PostViewResponse(post));
    }

    return "newPost";
  }


}
