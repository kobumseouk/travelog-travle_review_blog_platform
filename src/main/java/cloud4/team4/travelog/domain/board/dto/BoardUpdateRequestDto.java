package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDto {
    private String title;
    private String region;

    @Builder
    public BoardUpdateRequestDto(Board board) {
        this.title = board.getTitle();
        this.region = board.getRegion();
    }
}
