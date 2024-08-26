package cloud4.team4.travelog.domain.post.controller;

import cloud4.team4.travelog.domain.board.service.BoardService;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.dto.PostListViewResponse;
import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostResponseDto;
import cloud4.team4.travelog.domain.post.dto.PostViewResponse;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

  @GetMapping("/board/posts") // 상세 게시판
  public String listPosts(Model model,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "createdAt") String sortBy) {

    Sort sort = createSort(sortBy);
    Pageable pageable = PageRequest.of(page, 10, sort);
    Page<Post> postPage = postService.getAllPosts(pageable);

    Page<PostListViewResponse> postListViewResponsePage = postPage.map(PostListViewResponse::new);

    model.addAttribute("posts", postListViewResponsePage);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", postPage.getTotalPages());
    model.addAttribute("sortBy", sortBy);

    return "boardPost";
  }

  private Sort createSort(String sortBy) {
    if ("recommends".equals(sortBy)) {  // 추천순 + 최신순 정렬
      return Sort.by("recommends").descending().and(Sort.by("createdAt").descending());
    } else {  // 작성일 기준 최신순 정렬 (기본값)
      return Sort.by("createdAt").descending();
    }
  }

  @GetMapping("board/posts/{postId}")
  public String post(@PathVariable("postId") Long postId, Model model) {
    Post post = postService.getPostById(postId);
    postService.incrementViews(postId);  // 방문마다 조회수 증가

    model.addAttribute("commentRequestDto", new CommentRequestDto());
    model.addAttribute("post", new PostViewResponse(post));

    return "post";
  }

  @GetMapping("board/new-post")
  public String newPost(@RequestParam(required = false, name = "postId") Long postId, Model model) {
    // id 가 없으면 새롭게 블로그를 만든다.
    // id 가 있으면 수정
    if (postId == null) {
      model.addAttribute("post", new PostPostDto());
    } else {
      Post post = postService.getPostById(postId); // 조회하고 DTO객체에 감싸서 수정
      model.addAttribute("post", new PostViewResponse(post));
    }

    return "newPost";
  }


}
