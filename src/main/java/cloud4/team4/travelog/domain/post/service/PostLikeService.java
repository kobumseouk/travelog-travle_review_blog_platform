package cloud4.team4.travelog.domain.post.service;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.CommentLike;
import cloud4.team4.travelog.domain.comment.repository.CommentLikeRepository;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostLike;
import cloud4.team4.travelog.domain.post.repository.PostLikeRepository;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;
  private final MemberRepository memberRepository;
  private final PostRepository postRepository;

  @Transactional
  public void likePost(Long memberId, Long postId) {
    PostLike postLike = postLikeRepository.findPostLikeByMember_IdAndPost_Id(memberId, postId);

    // 좋아요 엔티티 생성
    if(postLike == null) {
      // member 설정 위해 단건 조회함
      Member member = memberRepository.findById(memberId)
          .orElseThrow(() -> new IllegalArgumentException("member not found"));

      // Post 설정 위해 단건 조회함
      Post post = postRepository.findById(postId)
          .orElseThrow(() -> new IllegalArgumentException("게시글을 찾지 못했습니다."));

      PostLike createdPostLike = new PostLike();
      createdPostLike.setMember(member);
      createdPostLike.setPost(post);

      post.incrementRecommends();
      postLikeRepository.save(createdPostLike);
    } else { // 좋아요 엔티티 삭제
      postLikeRepository.delete(postLike);

      Post post = postLike.getPost();
      post.decrementRecommends();
      postRepository.save(post);
    }
  }
}


