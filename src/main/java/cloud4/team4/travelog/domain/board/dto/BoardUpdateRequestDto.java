package cloud4.team4.travelog.domain.board.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private String description;

    public BoardUpdateRequestDto(Board board) {
        this.description = board.getDescription();
    }
}
