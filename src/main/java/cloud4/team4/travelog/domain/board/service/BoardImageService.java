package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardImageService {

    private final BoardRepository boardRepository;


    /* CREATE */

    // DB에 사진을 저장한다.
    @Transactional
    public void saveImage(Board board, MultipartFile image) throws Exception {
        try {
            // 파일이 비어있는지 확인
            if (image == null || image.isEmpty()) throw new RuntimeException("사진을 업로드해주세요");

            // 파일이 이미지인지 확인
            if (!isImageFile(image)) throw new IllegalArgumentException("사진 이외 파일은 업로드 불가");

            String imageNameUUID = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();
            board.setImage(imageNameUUID, image.getBytes());
            boardRepository.save(board);


        } catch (IOException e) {
            throw new RuntimeException("업로드 중 오류 발생", e);
        }
    }


    // (미사용)기존 사진 저장 로직.
    // static/uploads/board_images/ 디렉토리에 사진을 저장하고, DB에는 경로 정보만 저장한다.
    private String saveImage(MultipartFile image, String saveDir) throws IOException {
        // 파일명 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + image.getOriginalFilename();

        // 사진 파일 저장 경로 - 위에서 정의한 saveDir
        String filePath = saveDir + fileName;

        // DB에 저장할 경로
        String dbFilePath = "/uploads/board_images/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, image.getBytes());

        // DB에 저장할 경로 반환
        return dbFilePath;
    }



    /* UPDATE */
    // saveImage 메서드로 Update 로직을 같이 구현한다.



    /* DELETE */

    // 디렉토리에 저장된 사진을 삭제한다.
    public void deleteImage(Board board) {
            board.setImage(null, null);
    }



    /* 기타 메서드 */

//    // 사진 경로를 반환
//    public String findImagePathByBoardId(Long id) {
//        return boardImageRepository.findBoardImageByBoard_Id(id)
//                .stream()
//                .map(BoardImage::getImagePath)
//                .toString();
//    }
//
//    // 사진을 반환
//    public BoardImage findPhotoByBoardId(Long id) {
//        return boardImageRepository.findBoardImageByBoard_Id(id)
//                .stream()
//                .findFirst()
//                .orElseThrow();
//    }

    // 업로드된 파일이 이미지 파일인지 확인하는 메서드
    private boolean isImageFile(MultipartFile photo) {
        String contentType = photo.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

}
