package cloud4.team4.travelog.domain.comment.service;

import cloud4.team4.travelog.domain.comment.dto.CommentUpdateDto;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.CommentPhotos;
import cloud4.team4.travelog.domain.comment.exception.CommentPhotosSizeException;
import cloud4.team4.travelog.domain.comment.exception.CommentPhotosTypeException;
import cloud4.team4.travelog.domain.comment.repository.CommentPhotosRepository;
import cloud4.team4.travelog.domain.comment.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentPhotosService {

     private final CommentPhotosRepository commentPhotosRepository;
     private final CommentRepository commentRepository;


    @Transactional
    public void savePhotos(List<MultipartFile> photos, Comment comment) throws Exception{
         try {
             String saveDir = "src/main/resources/static/uploads/comment_photos/";

             // 사진 업로드 개수 > 5 인지 체크
             if(photos.size() > 5) {
                 throw new CommentPhotosSizeException("최대 사진 업로드 개수는 5개 입니다!");
             }

             // 업로드 파일의 타입이 사진인지 체크
             for (MultipartFile photo : photos) {
                 if(!isImageFile(photo)) throw new CommentPhotosTypeException("사진 이외의 파일은 업로드 불가능합니다!");
             }

             // 각 사진에 대해 업로드와 db에 저장
             for (MultipartFile photo : photos) {

                 // 파일이 비어있는 경우 패스
                 if(Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) continue;

                 // db 저장 경로
                 String dbFilePath = saveImage(photo, saveDir);

                 // CommentPhotos 엔티티 생성
                 CommentPhotos commentPhotos = new CommentPhotos(dbFilePath, comment);

                 // 생성한 엔티티 저장
                 commentPhotosRepository.save(commentPhotos);
             }
         } catch (IOException e) {
             throw new RuntimeException("파일 업로드 중 오류가 발생했습니다.", e);
         }
     }

     // 업로드 된 파일이 사진인지 검사
    private boolean isImageFile(MultipartFile photo) {
        String contentType = photo.getContentType();
        return contentType != null && (contentType.startsWith("image/"));
    }

    @Transactional
     public void updatePhotos(List<MultipartFile> photos, Comment comment) {

        // 기존의 이미지들은 모두 삭제
         commentPhotosRepository.deleteAll(findPhotosByCommentId(comment.getId()));

         // 업데이트된 이미지들 모두 저장
        try {
            savePhotos(photos, comment);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

     }

    // 이미지 파일을 저장하는 메서드
    private String saveImage(MultipartFile image, String saveDir) throws IOException {
        // 파일명 생성 -> 중복안되게 랜덤 값 + 기존 파일명으로 설정
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

        // 실제 파일이 저장될 경로: savePhotos 메서드의 파일 경로 + 파일명
        String filePath = saveDir + fileName;

        // DB에 저장할 경로: static 이전은 모두 자름
        String dbFilePath = "/uploads/comment_photos/" + fileName;

        Path path = Paths.get(filePath); // Path 객체 생성
        Files.createDirectories(path.getParent()); // 디렉토리 생성
        Files.write(path, image.getBytes()); // 디렉토리에 파일 저장

        return dbFilePath;
    }

    public List<String> findPhotosPathByCommentId(Long commentId) {
        return commentPhotosRepository.findCommentPhotosByComment_Id(commentId)
                .stream()
                .map(CommentPhotos::getImagePath)
                .toList();
    }

    public List<CommentPhotos> findPhotosByCommentId(Long commentId) {
        return commentPhotosRepository.findCommentPhotosByComment_Id(commentId);
    }
}
