package cloud4.team4.travelog.domain.post.controller;


import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostResponseDto;
import cloud4.team4.travelog.domain.post.dto.PostUpdateDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.dto.PostMapper;
import cloud4.team4.travelog.domain.post.exception.ResourceNotFoundException;
import cloud4.team4.travelog.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.*;
import java.lang.Void;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
  private final PostService postService;
  private final PostMapper postMapper;

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity<?> createPost(@Valid @ModelAttribute PostPostDto postPostDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("유효성 검사 오류");
    }

    try {
      Post createdPost = postService.createPost(postPostDto);
      PostResponseDto responseDto = postMapper.postToPostResponseDto(createdPost);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());  // 적절한 입력을 하지 못했을 때 오류
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류 발생");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }
  }

  @GetMapping
  public ResponseEntity<Page<PostResponseDto>> getAllPosts(
      @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    try {
      Page<Post> postPage = postService.getAllPosts(pageable);
      Page<PostResponseDto> postResponsePage = postPage.map(postMapper::postToPostResponseDto);
      return ResponseEntity.ok(postResponsePage);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPostById(@PathVariable("postId") Long postId) {
    try {
      Post post = postService.getPostById(postId);
      return ResponseEntity.ok(postMapper.postToPostResponseDto(post));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping(value = "update/{postId}", consumes = "multipart/form-data")
  public ResponseEntity<?> updatePost(@PathVariable("postId") Long postId,
                                      @Valid @ModelAttribute PostUpdateDto postUpdateDto,
                                      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body("유효성 검사 오류");
    }

    try {
      Post updatedPost = postService.updatePost(postId, postUpdateDto);
      return ResponseEntity.ok(postMapper.postToPostResponseDto(updatedPost));
    } catch (IllegalArgumentException e) {    // 적절한 입력을 하지 못했을 때 오류
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
    }
  }

  @DeleteMapping("delete/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
    try {
      postService.deletePost(postId);
      return ResponseEntity.ok().build();
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
