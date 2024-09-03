package cloud4.team4.travelog.domain.comment.service;

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
             // 업로드한 파일이 없다면 리턴
             if(photos == null) {
                 System.out.println("파일 없음");
                 return;
             }

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

                 // 이미지 이름 앞에 랜덤값 추가 (중복 안되게)
                 String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + photo.getOriginalFilename();

                 // CommentPhotos 엔티티 생성
                 CommentPhotos commentPhotos = new CommentPhotos(fileName, photo.getBytes(), comment);

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

    public List<CommentPhotos> findPhotosByCommentId(Long commentId) {
        return commentPhotosRepository.findCommentPhotosByComment_Id(commentId);
    }
}
