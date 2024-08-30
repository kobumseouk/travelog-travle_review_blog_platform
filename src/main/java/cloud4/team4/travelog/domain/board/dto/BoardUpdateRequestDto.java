package cloud4.team4.travelog.domain.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardUpdateRequestDto {
    private String description;
    private MultipartFile image;

}
