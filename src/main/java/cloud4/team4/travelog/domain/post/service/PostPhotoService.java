package cloud4.team4.travelog.domain.post.service;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostPhotoService {
  private final PostPhotoRepository postPhotoRepository;
  private final PostRepository postRepository;

  public void uploadPhotos(Post post, List<MultipartFile> photos, List<String> positions) {
    try {
      String uploadsDir = "src/main/resources/static/uploads/post_photos/";
      for (int i = 0; i < photos.size(); i++) {
        MultipartFile photo = photos.get(i);
        String position = positions.get(i);

        if (photo.isEmpty()) {
          continue;
        }

        String dbFilePath = savePhoto(photo, uploadsDir);
        PostPhoto postPhoto = new PostPhoto(post, dbFilePath, position);
        postPhotoRepository.save(postPhoto);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file", e);
    }
  }


  // 이미지 파일을 저장하는 메서드
  private String savePhoto(MultipartFile photo, String uploadsDir) throws IOException {
    // 파일 이름 생성
    String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + photo.getOriginalFilename();
    // 실제 파일이 저장될 경로
    String filePath = uploadsDir + fileName;
    // DB에 저장할 경로 문자열
    String dbFilePath = "/uploads/post_photos/" + fileName;

    Path path = Paths.get(filePath); // Path 객체 생성
    Files.createDirectories(path.getParent()); // 디렉토리 생성
    Files.write(path, photo.getBytes()); // 디렉토리에 파일 저장

    return dbFilePath;
  }

  @Transactional
  public void updatePhotos(Long postId, List<MultipartFile> photos, List<String> positions) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

    // 기존 사진 삭제
    postPhotoRepository.deleteAll(postPhotoRepository.findPostPhotoByPostId(postId));

    // 새 사진 업로드
    uploadPhotos(post, photos, positions);
  }

  public List<String> findPhotosPathByPostId(Long postId) {
    return postPhotoRepository.findPostPhotoByPostId(postId)
        .stream()
        .map(PostPhoto::getImagePath)
        .toList();
  }

  public List<PostPhoto> findPhotosByPostId(Long postId) {
    return postPhotoRepository.findPostPhotoByPostId(postId);
  }



}
