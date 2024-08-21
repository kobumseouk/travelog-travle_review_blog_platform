package cloud4.team4.Travelog.Post.Controller;


import cloud4.team4.Travelog.Post.Dto.PostPostDto;
import cloud4.team4.Travelog.Post.Dto.PostResponseDto;
import cloud4.team4.Travelog.Post.Entity.Post;
import cloud4.team4.Travelog.Post.Mapper.PostMapper;
import cloud4.team4.Travelog.Post.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.*;
import java.lang.Void;

@RestController
@RequestMapping("/posts")
public class PostController {
  private final PostService postService;
  private final PostMapper postMapper;


  public PostController(PostService postService, PostMapper postMapper) {
    this.postService = postService;
    this.postMapper = postMapper;
  }

  // 게시글 생성
  @PostMapping
  public ResponseEntity<PostResponseDto> createPost(@RequestBody PostPostDto postPostDto) {
    Post post = postMapper.postPostDtoToPost(postPostDto);
    Post createdPost = postService.createPost(post);

    return new ResponseEntity<>(postMapper.postToPostResponseDto(createdPost), HttpStatus.CREATED);
  }

  // 모든 게시글 조회
  @GetMapping
  public ResponseEntity<List<PostResponseDto>> getAllPosts() {
    List<PostResponseDto> postResponses = postService.getAllPosts()
        .stream()
        .map(postMapper::postToPostResponseDto)
        .collect(Collectors.toList());

    return new ResponseEntity<>(postResponses, HttpStatus.OK);
  }

  // 특정 게시글 조회
  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Long postId) {
    Post post = postService.getPostById(postId);

    if (post != null) {
      return new ResponseEntity<>(postMapper.postToPostResponseDto(post), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // 게시글 수정
  @PutMapping("/{postId}")
  public ResponseEntity<PostResponseDto> updatePost(@PathVariable("postId") Long postId, @RequestBody PostPostDto postPostDto) {
    Post post = postMapper.postPostDtoToPost(postPostDto);
    Post updatedPost = postService.updatePost(postId, post);

    if (updatedPost != null) {
      return new ResponseEntity<>(postMapper.postToPostResponseDto(updatedPost), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // 게시글 삭제
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
    boolean deleted = postService.deletePost(postId);
    if (deleted) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
