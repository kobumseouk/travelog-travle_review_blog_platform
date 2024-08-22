package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CommentUpdateDto {

    private String content;

    private List<MultipartFile> photos;

}
