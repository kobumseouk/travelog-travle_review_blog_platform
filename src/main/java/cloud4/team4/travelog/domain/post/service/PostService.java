package cloud4.team4.travelog.domain.post.service;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import cloud4.team4.travelog.domain.board.service.BoardService;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import cloud4.team4.travelog.domain.post.dto.PostMapper;
import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostUpdateDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.exception.ResourceNotFoundException;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

  @Autowired
  private final PostRepository postRepository;
  private final PostPhotoService postPhotoService;
  private final MemberRepository memberRepository;
  private final BoardRepository boardRepository;


  // 게시글 생성
  @Transactional
  public Post createPost(PostPostDto postPostDto) {
    Post post = PostMapper.INSTANCE.postPostDtoToPost(postPostDto);

    // post.setTitle(postPostDto.getTitle());
    // post.setContent(postPostDto.getContent());

    Member member = memberRepository.findById(postPostDto.getMemberId())
        .orElseThrow(() -> new ResourceNotFoundException("member not found"));

    Board board = boardRepository.findById(postPostDto.getBoardId())
        .orElseThrow(() -> new ResourceNotFoundException("board not found"));

    post.setMember(member);
    post.setBoard(board);

    // post.setPeriodStart(postPostDto.getPeriodStart());
    // post.setPeriodEnd(postPostDto.getPeriodEnd());
    post.setCreatedAt(LocalDateTime.now());
    post.setEditedAt(LocalDateTime.now());
    post.setViews(0);
    post.setRecommends(0);

    Post createdPost = postRepository.save(post);
    postPhotoService.uploadPhotos(createdPost, postPostDto.getPhotos(), postPostDto.getPhotoPositions());
    return createdPost;
  }

  // 모든 게시글 조회
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  // 특정 게시글 조회
  public Post getPostById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
  }

  // 게시글 수정
  @Transactional
  public Post updatePost(Long postId, PostUpdateDto postDetails) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

    post.setTitle(postDetails.getTitle());
    post.setContent(postDetails.getContent());
    post.setPeriodStart(postDetails.getPeriodStart());
    post.setPeriodEnd(postDetails.getPeriodEnd());
    post.setEditedAt(LocalDateTime.now());

    postPhotoService.updatePhotos(postId, postDetails.getPhotos(), postDetails.getPhotoPositions());
    return postRepository.save(post);
  }

  // 게시글 삭제
  @Transactional
  public void deletePost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    postRepository.delete(post);
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
