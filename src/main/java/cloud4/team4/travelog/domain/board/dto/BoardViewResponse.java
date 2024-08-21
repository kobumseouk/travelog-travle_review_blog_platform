package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardViewResponse {
    private String title;
    private String region;

//    @Builder
    public BoardViewResponse(Board board) {
        this.title = board.getTitle();
        this.region = board.getRegion();
    }
}
