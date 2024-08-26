package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.entity.BoardImage;
import cloud4.team4.travelog.domain.board.repository.BoardImageRepository;
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
    private final BoardImageRepository boardImageRepository;
    private final BoardRepository boardRepository;


    /* CREATE */

    // 사진 추가
    @Transactional
    public void saveImage(MultipartFile image, Board board) throws Exception {
        try {
            // 사진 저장 디렉토리
            String saveDir = "src/main/resources/static/uploads/board_images";

            // 파일이 비어있는지 확인
            if (image == null || image.isEmpty()) return;

            // 파일이 이미지인지 확인
            if (!isImageFile(image)) throw new IllegalArgumentException("사진 이외 파일은 업로드 불가");

            // 경로를 DB에 저장
            String dbFilePath = saveImage(image, saveDir);

            BoardImage boardImage = new BoardImage(dbFilePath, board);

            boardImageRepository.save(boardImage);
        } catch (IOException e) {
            throw new RuntimeException("업로드 중 오류 발생", e);
        }
    }

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

    @Transactional
    public void T_updateImage(MultipartFile image, Board board) {

        // 기존 이미지 삭제
        boardImageRepository.delete(findPhotoByBoardId(board.getId()));

        // 이미지 업데이트
        try {
            saveImage(image, board);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    /* 기타 메서드 */

    // 사진 경로를 반환
    public String findImagePathByBoardId(Long id) {
        return boardImageRepository.findBoardImageByBoard_Id(id)
                .stream()
                .map(BoardImage::getImagePath)
                .toString();
    }

    // 사진을 반환
    public BoardImage findPhotoByBoardId(Long id) {
        return boardImageRepository.findBoardImageByBoard_Id(id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    // ToDo: 이미지 파일인지 확인하는 메서드
    private boolean isImageFile(MultipartFile photo) {
        String contentType = photo.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

}
