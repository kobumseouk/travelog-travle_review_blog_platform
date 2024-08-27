package cloud4.team4.travelog.domain.comment.dto;

import lombok.Data;

@Data
public class CommentPagingInfo {

    private int commentPagingSize;
    private String commentSort;
}
