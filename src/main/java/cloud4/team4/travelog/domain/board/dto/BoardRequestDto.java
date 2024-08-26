package cloud4.team4.travelog.domain.board.dto;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardRequestDto {
    private Long boardId;
    private String regionMajor;
    private String regionMiddle;
    private String description;

    // Todo: 사진 추가
    private MultipartFile photo;

}
