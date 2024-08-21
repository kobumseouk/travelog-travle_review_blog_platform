package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardUpdateResponseDto {
    private final String title;
    private final String region;

    @Builder
    public BoardUpdateResponseDto(Board board) {
        this.title = board.getTitle();
        this.region = board.getRegion();
    }
}
