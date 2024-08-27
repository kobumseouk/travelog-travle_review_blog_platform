package cloud4.team4.travelog.domain.board.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class BoardDescRequestDto {
    private String description;

    @Builder
    public BoardDescRequestDto(String description) {
        this.description = description;
    }
}
