package cloud4.team4.travelog.domain.post.service;

import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostUpdateDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

  @Autowired
  private final PostRepository postRepository;
  private final PostPhotoService postPhotoService;



  // 게시글 생성
  @Transactional
  public Post createPost(Long postId, PostPostDto postPostDto) {
    Post post = getPostById(postId);

    post.setCreatedAt(LocalDateTime.now());
    post.setEditedAt(LocalDateTime.now());
    post.setViews(0);
    post.setRecommends(0);

    Post createdPost = postRepository.save(post);
    postPhotoService.uploadPhotos(createdPost, postPostDto.getPhotos());
    return createdPost;
  }

  // 모든 게시글 조회
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  // 특정 게시글 조회
  public Post getPostById(Long postId) {
    return postRepository.findById(postId).orElse(null);
  }

  // 게시글 수정
  @Transactional
  public Post updatePost(Long postId, PostUpdateDto postDetails, List<MultipartFile> photos) {
    postPhotoService.updatePhotos(postId, photos);

    return postRepository.findById(postId)
        .map(post -> {
          post.setTitle(postDetails.getTitle());
          post.setContent(postDetails.getContent());
          post.setPeriodStart(postDetails.getPeriodStart());
          post.setPeriodEnd(postDetails.getPeriodEnd());
          post.setEditedAt(LocalDateTime.now());   // -> 수정 시마다 업데이트
          return postRepository.save(post);
        })
        .orElseThrow(() -> new IllegalArgumentException("not found : " + postId));
  }

  // 게시글 삭제
  @Transactional
  public void deletePost(Long postId) {
    postRepository.deleteById(postId);
  }

  // 조회수 증가
  @Transactional
  public void incrementViews(Long postId) {
    postRepository.findById(postId)
        .ifPresent(post -> {
          post.setViews(post.getViews() + 1);
          postRepository.save(post);
        });
  }

  // 추천수 증가
  @Transactional
  public void incrementRecommends(Long postId) {
    postRepository.findById(postId)
        .ifPresent(post -> {
          post.setRecommends(post.getRecommends() + 1);
          postRepository.save(post);
        });
  }

  // 제목으로 게시글 검색
  public List<Post> searchPostsByTitle(String keyword) {
    return postRepository.findByTitleContaining(keyword);
  }

}