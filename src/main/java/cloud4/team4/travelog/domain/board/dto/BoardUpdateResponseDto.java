package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardUpdateResponseDto {
    private final String description;

    @Builder
    public BoardUpdateResponseDto(Board board) {
        this.description = board.getDescription();
    }
}
