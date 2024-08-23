package cloud4.team4.travelog.domain.board.service;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.board.entity.BoardPhoto;
import cloud4.team4.travelog.domain.board.repository.BoardPhotoRepository;
import cloud4.team4.travelog.domain.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardPhotoService {
    private final BoardPhotoRepository boardPhotoRepository;
    private final BoardRepository boardRepository;

    // Todo: 사진 추가
    @Transactional
    public void savePhoto(MultipartFile photo, Board board) throws Exception {
        try {
            // 사진 저장 디렉토리
            String saveDir = "src/main/resources/static/uploads/board_photos";

            // 파일이 비어있는지 확인
            if (photo == null || photo.isEmpty()) return;

            // 파일이 이미지인지 확인
            if (!isImageFile(photo)) throw new IllegalArgumentException("사진 이외 파일은 업로드 불가");

            // 경로를 DB에 저장
            String dbFilePath = savePhoto(photo, saveDir);

            BoardPhoto boardPhoto = new BoardPhoto(dbFilePath, board);

            boardPhotoRepository.save(boardPhoto);
        } catch (IOException e) {
            throw new RuntimeException("업로드 중 오류 발생", e);
        }
    }

    private String savePhoto(MultipartFile photo, String saveDir) throws IOException {
        // 파일명 생성
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + photo.getOriginalFilename();

        // 사진 파일 저장 경로 - 위에서 정의한 saveDir
        String filePath = saveDir + fileName;

        // DB에 저장할 경로
        String dbFilePath = "/uploads/board_photos/" + fileName;

        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, photo.getBytes());

        // DB에 저장할 경로 반환
        return dbFilePath;
    }

    // 사진 경로를 반환
    public String findPhotoPathByBoardId(Long id) {
        return boardPhotoRepository.findBoardPhotoByBoard_Id(id)
                .stream()
                .map(BoardPhoto::getImagePath)
                .toString();
    }

    // 사진을 반환
    public BoardPhoto findPhotoByBoardId(Long id) {
        return boardPhotoRepository.findBoardPhotoByBoard_Id(id)
                .stream()
                .findFirst()
                .orElseThrow();
    }

    // ToDo: 이미지 파일인지 확인하는 메서드
    private boolean isImageFile(MultipartFile photo) {
        String contentType = photo.getContentType();
        return contentType != null && contentType.startsWith("image/");
    };

}
