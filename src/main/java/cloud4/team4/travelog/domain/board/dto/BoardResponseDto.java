package cloud4.team4.travelog.domain.board.dto;


import cloud4.team4.travelog.domain.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {

    private Long id;
    private String description;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.description = board.getDescription();
    }
}
