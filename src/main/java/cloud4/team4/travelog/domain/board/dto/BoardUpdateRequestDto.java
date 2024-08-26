package cloud4.team4.travelog.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String description;
    private MultipartFile image;

    public BoardUpdateRequestDto(String description, MultipartFile image) {
        this.description = description;
        this.image = image;
    }
}
