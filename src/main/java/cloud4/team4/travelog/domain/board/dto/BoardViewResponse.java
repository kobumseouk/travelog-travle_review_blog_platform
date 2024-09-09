package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardViewResponse {
    private String description;
    private String regionMajor;
    private String regionMiddle;

    //    @Builder
    public BoardViewResponse(Board board) {
        this.description = board.getDescription();
        this.regionMajor = board.getRegionMajor();
        this.regionMiddle = board.getRegionMiddle();
    }
}
