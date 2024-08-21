package cloud4.team4.travelog.domain.post.repository;

import cloud4.team4.travelog.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {



  // 제목에 특정 키워드가 포함된 게시글 검색
  List<Post> findByTitleContaining(String keyword);

  // List<Post> findPostByPostId(Long postId); -> findById(Long postId)
  //void deleteByPostId(Long postId); -> deleteById(Long postId)

  // ID로 특정 게시글 존재 여부 확인
  //boolean existsByPostId(Long postId); -> existsById(Long postId);

  // save(Post post) - 게시글 저장 (생성 및 수정)
  // findAll() - 모든 게시글 조회


}
