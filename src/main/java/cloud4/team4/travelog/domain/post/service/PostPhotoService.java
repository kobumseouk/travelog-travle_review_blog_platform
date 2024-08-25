package cloud4.team4.travelog.domain.post.service;

import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import cloud4.team4.travelog.domain.post.exception.FileStorageException;
import cloud4.team4.travelog.domain.post.exception.ResourceNotFoundException;
import cloud4.team4.travelog.domain.post.repository.PostPhotoRepository;
import cloud4.team4.travelog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostPhotoService {
  private final PostPhotoRepository postPhotoRepository;
  private final PostRepository postRepository;

  @Value("${upload.dir}")
  private String uploadDir;

  @Transactional
  public void uploadPhoto(Post post, MultipartFile photo, String position) {
    if (photo == null || photo.isEmpty()) {
      throw new IllegalArgumentException("사진은 필수입니다.");
    }

    if (position == null || position.trim().isEmpty()) {
      throw new IllegalArgumentException("사진의 위치 정보는 필수입니다.");
    }

    try {
      String dbFilePath = savePhoto(photo);
      PostPhoto postPhoto = new PostPhoto(post, dbFilePath, position);
      postPhotoRepository.save(postPhoto);
    } catch (IOException e) {
      throw new FileStorageException("파일을 저장할 수 없습니다. " + photo.getOriginalFilename(), e);
    }
  }


  // 이미지 파일을 저장하는 메서드
  private String savePhoto(MultipartFile photo) throws IOException {
    if (!isImageFile(photo)) {
      throw new IllegalArgumentException("File must be an image");
    }

    String fileName = UUID.randomUUID().toString() + getFileExtension(photo.getOriginalFilename());
    Path targetLocation = Paths.get(uploadDir).resolve(fileName);
    Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    return "/uploads/post_photos/" + fileName;
  }

  private boolean isImageFile(MultipartFile file) {
    String contentType = file.getContentType();
    return contentType != null && contentType.startsWith("image/");
  }

  private String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }


  @Transactional
  public void updatePhoto(Long postId, MultipartFile photo, String position) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException(postId + ": 해당 아이디로 게시글을 찾을 수 없습니다."));

    // 기존 사진 삭제
    postPhotoRepository.deleteByPost(post);

    // 새 사진 업로드
    uploadPhoto(post, photo, position);
  }

  public PostPhoto findPhotoByPostId(Long postId) {
    return postPhotoRepository.findByPost_PostId(postId)
        .orElse(null);
  }

}
