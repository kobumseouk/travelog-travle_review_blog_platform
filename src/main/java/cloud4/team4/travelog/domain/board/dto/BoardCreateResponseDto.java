package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardCreateResponseDto {
    private final long id;
    private final String title;
    private final String region;

    public BoardCreateResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.region = board.getRegion();
    }
}
