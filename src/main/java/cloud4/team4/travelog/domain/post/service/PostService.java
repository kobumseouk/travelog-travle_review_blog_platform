package cloud4.team4.travelog.domain.post.service;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import cloud4.team4.travelog.domain.post.dto.PostListViewResponse;
import cloud4.team4.travelog.domain.post.dto.PostMapper;
import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostUpdateDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.exception.ResourceNotFoundException;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final PostPhotoService postPhotoService;
  private final MemberRepository memberRepository;
  private final BoardRepository boardRepository;
  private final PostMapper postMapper;

  // 게시글 생성
  @Transactional
  public Post createPost(PostPostDto postPostDto) throws IOException {
    Post post = postMapper.postPostDtoToPost(postPostDto);

    // post.setTitle(postPostDto.getTitle());
    // post.setContent(postPostDto.getContent());

    Member member = memberRepository.findById(postPostDto.getMemberId())
        .orElseThrow(() -> new ResourceNotFoundException("회원을 찾을 수 없다."));

    Board board = boardRepository.findById(postPostDto.getBoardId())
        .orElseThrow(() -> new ResourceNotFoundException("게시판을 찾을 수 없다."));

    post.setMember(member);
    post.setBoard(board);

    // post.setPeriodStart(postPostDto.getPeriodStart());
    // post.setPeriodEnd(postPostDto.getPeriodEnd());
    post.setCreatedAt(LocalDateTime.now());
    post.setEditedAt(LocalDateTime.now());
    post.setViews(0);
    post.setRecommends(0);

    Post createdPost = postRepository.save(post);
    // 사진이 있는 경우에만 올림
    if (postPostDto.getPhotos() != null && !postPostDto.getPhotos().isEmpty()) {
      postPhotoService.uploadPhoto(createdPost, postPostDto.getPhotos());
    }
    return createdPost;
  }

  // 모든 게시글 조회
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  // 특정 게시글 조회
  public Post getPostById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException(postId + ": 해당 아이디로 게시글을 찾을 수 없습니다."));
  }

  // 게시글 수정
  @Transactional
  public Post updatePost(Long postId, PostUpdateDto postDetails) throws IOException {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException(postId + ": 해당 아이디로 게시글을 찾을 수 없습니다."));

    post.setTitle(postDetails.getTitle());
    post.setContent(postDetails.getContent());
    post.setPeriodStart(postDetails.getPeriodStart());
    post.setPeriodEnd(postDetails.getPeriodEnd());
    post.setEditedAt(LocalDateTime.now());

    postPhotoService.updatePhoto(postId, postDetails.getPhotos());
    return postRepository.save(post);
  }

  // 게시글 삭제
  @Transactional
  public void deletePost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException(postId + ": 해당 아이디로 게시글을 찾을 수 없습니다."));
    postRepository.delete(post);
  }

  public Page<PostListViewResponse> getPostsByRegionMiddle(String regionMiddle, String searchType, String keyword, Pageable pageable) {
    Specification<Post> spec = Specification.where(null);

    if (regionMiddle != null && !regionMiddle.isEmpty()) {
      spec = spec.and((root, query, cb) -> cb.equal(root.get("board").get("regionMiddle"), regionMiddle));
    }

    // 검색 분류
    if (searchType != null && keyword != null && !keyword.isEmpty()) {
      if ("title".equals(searchType)) {
        spec = spec.and((root, query, cb) -> cb.like(root.get("title"), "%" + keyword + "%"));
      } else if ("content".equals(searchType)) {
        spec = spec.and((root, query, cb) -> cb.like(root.get("content"), "%" + keyword + "%"));
      } else if ("regionMiddle".equals(searchType)) {
        spec = spec.and((root, query, cb) -> cb.like(root.get("board").get("regionMiddle"), "%" + keyword + "%"));
      }
    }

    Page<Post> posts = postRepository.findAll(spec, pageable);
    return posts.map(PostListViewResponse::new);
  }

  // 정렬 방식 선택
  public Sort createSort(String sortBy) {
    if ("recommends".equals(sortBy)) {  // 추천순 + 최신순 정렬
      return Sort.by("recommends").descending().and(Sort.by("createdAt").descending());
    } else {  // 작성일 기준 최신순 정렬 (기본값)
      return Sort.by("createdAt").descending();
    }
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

  // 조회수 감소(새로고침 방지)
  @Transactional
  public void decreaseViews(Long postId) {
    postRepository.findById(postId)
        .ifPresent(post -> {
          post.setViews(post.getViews() - 1);
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

}
