package cloud4.team4.travelog.domain.post.controller;


import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostResponseDto;
import cloud4.team4.travelog.domain.post.dto.PostUpdateDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.dto.PostMapper;
import cloud4.team4.travelog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.*;
import java.lang.Void;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
  private final PostService postService;
  private final PostMapper postMapper;

  // 게시글 생성
  @PostMapping
  public ResponseEntity<PostResponseDto> createPost(@PathVariable("postId") Long postId,
                                                    @ModelAttribute PostPostDto postPostDto) {
    Post createdPost = postService.createPost(postId, postPostDto);
    PostResponseDto responseDto = postMapper.postToPostResponseDto(createdPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
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
  @PutMapping(value = "update/{postId}", consumes = "multipart/form-data")
  public ResponseEntity<PostResponseDto> updatePost(@PathVariable("postId") Long postId,
                                                    @ModelAttribute PostUpdateDto postUpdateDto,
                                                    @RequestParam(value = "photos", required = false) List<MultipartFile> newPhotos) {

    Post updatedPost = postService.updatePost(postId, postUpdateDto, newPhotos);

    if (updatedPost != null) {
      return new ResponseEntity<>(postMapper.postToPostResponseDto(updatedPost), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // 게시글 삭제
  @DeleteMapping("delete/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
    postService.deletePost(postId);

    return ResponseEntity.ok().build();
  }

}
