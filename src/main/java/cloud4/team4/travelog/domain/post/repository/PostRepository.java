package cloud4.team4.travelog.domain.post.repository;

import cloud4.team4.travelog.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Specification<Post> spec, Pageable pageable);


  // List<Post> findPostByPostId(Long postId); -> findById(Long postId)
  //void deleteByPostId(Long postId); -> deleteById(Long postId)

  // ID로 특정 게시글 존재 여부 확인
  //boolean existsByPostId(Long postId); -> existsById(Long postId);

  // save(Post post) - 게시글 저장 (생성 및 수정)
  // findAll() - 모든 게시글 조회


}
