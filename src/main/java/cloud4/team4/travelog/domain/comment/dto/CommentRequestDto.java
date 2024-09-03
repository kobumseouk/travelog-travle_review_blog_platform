package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentRequestDto {

    private String content;

    private Long memberId;

    private List<MultipartFile> photos;

    private Long rating;
}
