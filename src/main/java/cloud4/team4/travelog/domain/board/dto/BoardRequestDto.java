package cloud4.team4.travelog.domain.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardRequestDto {
    private String regionMajor;
    // private String regionMiddle;
    private String description;
    private String boardCategory;
    private MultipartFile image;

}
