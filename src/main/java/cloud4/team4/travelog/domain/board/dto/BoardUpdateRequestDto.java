package cloud4.team4.travelog.domain.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String description;
    private MultipartFile image;
    private String regionMajor;

    public BoardUpdateRequestDto(String description, String regionMajor) {
        this.description = description;
        this.regionMajor = regionMajor;
    }
}
