package cloud4.team4.travelog.domain.post.controller;

import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.service.BoardService;
import cloud4.team4.travelog.domain.comment.dto.CommentPagingInfo;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.dto.PostListViewResponse;
import cloud4.team4.travelog.domain.post.dto.PostMapper;
import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.service.PostLikeService;
import cloud4.team4.travelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class PostViewController {
  private final BoardService boardService;
  private final PostService postService;
  private final CommentService commentService;
  private final PostLikeService postLikeService;
  private final PostMapper postMapper;

  @GetMapping("/boards/{regionMajor}")
  public String listPosts(@PathVariable String regionMajor,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "createdAt") String sortBy,
                          @RequestParam(required = false) String searchType,
                          @RequestParam(required = false) String keyword,
                          Model model) {

    Pageable pageable = PageRequest.of(page - 1, 10, postService.createSort(sortBy));
    Page<PostListViewResponse> posts = postService.getPostsByRegionMajor(regionMajor, searchType, keyword, pageable);

    // 모든 게시판 중복 제거 후 찾기
    List<String> uniqueRegionMajors = boardService.getAllBoards().stream()
        .map(BoardViewResponse::getRegionMajor)
        .distinct()
        .collect(Collectors.toList());
    List<BoardViewResponse> MiddleBoards = boardService.getMiddleBoards(regionMajor);

    model.addAttribute("posts", posts);
    model.addAttribute("currentPage", posts.getNumber() + 1);
    model.addAttribute("totalPages", posts.getTotalPages());
    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("allRegionMajors", uniqueRegionMajors);
    model.addAttribute("middleBoards", MiddleBoards);
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("searchType", searchType);
    model.addAttribute("keyword", keyword);

    return "boardPosts";
  }


  @GetMapping("/boards/{regionMajor}/posts/{postId}")
  public String post(@PathVariable("regionMajor") String regionMajor,
                     @PathVariable("postId") Long postId,
                     @RequestParam(required = false, value = "commentPage", defaultValue = "1") int commentPage,
                     @ModelAttribute("commentPagingInfo") CommentPagingInfo commentPagingInfo,
                     Model model) {
    Post post = postService.getPostById(postId);

    postService.incrementViews(postId);  // 방문마다 조회수 증가


    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("commentRequestDto", new CommentRequestDto());
    model.addAttribute("post", postMapper.postToPostResponseDto(post));
    model.addAttribute("comments", commentService.findPagedCommentsByPostId(postId, commentPage, commentPagingInfo));

    if(post.getBoard().getBoardCategory().equals("여행후기")) model.addAttribute("averageRating", commentService.getAverageRating(postId));

    return "post";
  }


  @GetMapping("/boards/{regionMajor}/posts/{postId}/like")
  public String likePost(@PathVariable String regionMajor,
                         @PathVariable("postId") Long postId,
                         Model model) {

    // 로그인 한 멤버의 id
    Long memberId = (Long) model.getAttribute("loginMember");
    postLikeService.likePost(memberId, postId);

    return "redirect:/boards/" + regionMajor + "/posts/" + postId;
  }


  @GetMapping("/boards/{regionMajor}/posts/post-new")
  public String newPost(@PathVariable String regionMajor,
                        @RequestParam(required = false, name = "postId") Long postId,
                        Model model) {

    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("post", new PostPostDto());

    return "newPost";

  }

  @GetMapping("/boards/{regionMajor}/posts/post-modify/{postId}")
  public String modifyPost(@PathVariable String regionMajor,
                           @PathVariable("postId") Long postId,
                           Model model) {

    Post post = postService.getPostById(postId);

    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("post", postMapper.postToPostResponseDto(post));

    return "newPost";
  }

  @ModelAttribute("loginMember")
  public Long loginMember() {
    return 1L;
  }
}
