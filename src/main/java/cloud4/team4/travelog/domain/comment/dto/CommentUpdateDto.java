package cloud4.team4.travelog.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateDto {

    private String content;

    private List<MultipartFile> photos;

    public CommentUpdateDto(String content) {
        this.content = content;
        this.photos = null;
    }
}
