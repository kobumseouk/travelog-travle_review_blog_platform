package cloud4.team4.travelog.domain.post.controller;

import cloud4.team4.travelog.domain.board.dto.BoardViewResponse;
import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.service.BoardService;
import cloud4.team4.travelog.domain.comment.dto.CommentPagingInfo;
import cloud4.team4.travelog.domain.comment.dto.CommentRequestDto;
import cloud4.team4.travelog.domain.comment.service.CommentService;
import cloud4.team4.travelog.domain.post.dto.*;
import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.post.dto.PostListViewResponse;
import cloud4.team4.travelog.domain.post.dto.PostMapper;
import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.service.PostLikeService;
import cloud4.team4.travelog.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  @GetMapping("/board/{regionMajor}/{boardId}/posts")
  public String listPosts(@PathVariable(name = "regionMajor") String regionMajor,
                          @PathVariable(name = "boardId") Long boardId,
                          @RequestParam(name = "regionMiddle", required = false) String regionMiddle,
                          @RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy,
                          @RequestParam(name = "searchType", required = false) String searchType,
                          @RequestParam(name = "keyword", required = false) String keyword,
                          Model model) {

    Board board = boardService.getBoardById(boardId);
    String defaultRegionMiddle = board.getRegionMiddle();

    // regionMiddle이 null이면 board의 regionMiddle을 사용
    if (regionMiddle == null) {
      regionMiddle = defaultRegionMiddle;
    }

    Pageable pageable = PageRequest.of(page - 1, 10, postService.createSort(sortBy));
    Page<PostListViewResponse> posts = postService.getPostsByRegionMiddle(regionMiddle, searchType, keyword, pageable);

    List<BoardViewResponse> middleBoards = boardService.getMiddleBoards(regionMajor);

    model.addAttribute("posts", posts);
    model.addAttribute("currentPage", posts.getNumber() + 1);
    model.addAttribute("totalPages", posts.getTotalPages());
    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("boardId", boardId);
    model.addAttribute("boardCategory", board.getBoardCategory());
    model.addAttribute("sortBy", sortBy);
    model.addAttribute("searchType", searchType);
    model.addAttribute("keyword", keyword);
    model.addAttribute("regionMiddle", regionMiddle);
    if(board.getBoardCategory().equals("여행후기")) {
      model.addAttribute("MiddleBoards", middleBoards);
    }

    return "boardPosts";
  }


  @GetMapping("/board/{regionMajor}/{boardId}/posts/{postId}")
  public String post(@PathVariable("regionMajor") String regionMajor,
                     @PathVariable("boardId") Long boardId,
                     @PathVariable("postId") Long postId,
                     @RequestParam(required = false, value = "commentPage", defaultValue = "1") int commentPage,
                     @ModelAttribute("commentPagingInfo") CommentPagingInfo commentPagingInfo,
                     Model model) {

    Post post = postService.getPostById(postId);
    postService.incrementViews(postId);  // 방문마다 조회수 증가


    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("boardId", boardId);
    model.addAttribute("commentRequestDto", new CommentRequestDto());
    model.addAttribute("post", postMapper.postToPostResponseDto(post));
    model.addAttribute("comments", commentService.findPagedCommentsByPostId(postId, commentPage, commentPagingInfo));

    if(post.getBoard().getBoardCategory().equals("여행후기")) model.addAttribute("averageRating", commentService.getAverageRating(postId));

    return "post";
  }


  @GetMapping("/board/{regionMajor}/{boardId}/posts/{postId}/like")
  public String likePost(@PathVariable("regionMajor") String regionMajor,
                         @PathVariable("boardId") Long boardId,
                         @PathVariable("postId") Long postId,
                         Model model) {

    // 로그인 한 멤버의 id
    Long memberId = (Long) model.getAttribute("loginMember");

    postService.decreaseViews(postId);
    postLikeService.likePost(memberId, postId);

    return "redirect:/board/" + regionMajor + "/" + boardId + "/posts/" + postId;
  }

  // 게시글 작성/수정
  @GetMapping("/board/{regionMajor}/{boardId}/posts/post-new")
  public String newPost(@PathVariable("regionMajor") String regionMajor,
                        @PathVariable("boardId") Long boardId,
                        @RequestParam(required = false, name = "postId") Long postId,
                        Model model) {

    // 로그인 한 멤버의 id
    Long memberId = (Long) model.getAttribute("loginMember");

    model.addAttribute("regionMajor", regionMajor);
    model.addAttribute("boardId", boardId);

    if (postId == null) { // 게시글 작성
      PostPostDto postDto = new PostPostDto();
      postDto.setBoardId(boardId);  // boardId 설정
      postDto.setMemberId(memberId);
      model.addAttribute("post", postDto);
    } else {  // 게시글 수정
      Post post = postService.getPostById(postId);
      PostUpdateDto updateDto = postMapper.postToPostUpdateDto(post);
      updateDto.setId(postId);  // postId 설정
      updateDto.setBoardId(boardId);  // boardId 설정
      updateDto.setMemberId(memberId);
      model.addAttribute("post", updateDto);
    }

    return "newPost";

  }

  @ModelAttribute("loginMember")
  public Long loginMemberId(HttpSession session) {
    // 세션에서 로그인한 멤버의 id 값 가져옴
    MemberDto memberDto = (MemberDto) session.getAttribute("member");
    if(memberDto == null) return null;
    return memberDto.getId();
  }
}