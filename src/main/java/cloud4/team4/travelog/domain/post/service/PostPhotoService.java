package cloud4.team4.travelog.domain.post.service;

import cloud4.team4.travelog.domain.comment.exception.CommentPhotosTypeException;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import cloud4.team4.travelog.domain.post.exception.ResourceNotFoundException;
import cloud4.team4.travelog.domain.post.repository.PostPhotoRepository;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostPhotoService {
  private final PostPhotoRepository postPhotoRepository;
  private final PostRepository postRepository;

  @Transactional
  public void uploadPhoto(Post post, List<MultipartFile> photos) throws IOException {
    try {
      if (photos == null || photos.isEmpty()) {
        return;
      }
      if (photos.size() > 3) {   // 업로드 사진 개수 제한
        throw new IllegalArgumentException("최대 사진 업로드 개수는 3개입니다!");
      }

      for (MultipartFile photo : photos) {
        if (photo.isEmpty()) continue;

        if (!isImageFile(photo)) {
          throw new IllegalArgumentException("사진 이외의 파일은 업로드 불가능합니다!");
        }

        String imageName = UUID.randomUUID().toString().replace("-", "") + "_" + photo.getOriginalFilename();
        PostPhoto postPhoto = new PostPhoto(imageName, photo.getBytes(), post);
        postPhotoRepository.save(postPhoto);
      }
    } catch (IOException e) {
      throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
    }
  }

  private boolean isImageFile(MultipartFile file) {
    String contentType = file.getContentType();
    return contentType != null && contentType.startsWith("image/");
  }

  @Transactional
  public void updatePhoto(Long postId, List<MultipartFile> photos) throws IOException {

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException(postId + ": 해당 아이디로 게시글을 찾을 수 없습니다."));

    // 기존 이미지 삭제
    List<PostPhoto> existPhotos = findPhotosByPostId(postId);
    if (!existPhotos.isEmpty()) {
      postPhotoRepository.deleteAll(existPhotos);
    }

    // 새 이미지 업로드
    if (photos != null && !photos.isEmpty()) {
      uploadPhoto(post, photos);
    }

  }

  public List<PostPhoto> findPhotosByPostId(Long postId) {
    return postPhotoRepository.findAllPhotosByPost_Id(postId);
  }

}