package cloud4.team4.travelog.domain.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class BoardImageRequestDto {
    private MultipartFile image;

    public BoardImageRequestDto(MultipartFile image) {
        this.image = image;
    }
}
