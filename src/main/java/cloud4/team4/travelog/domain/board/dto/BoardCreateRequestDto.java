package cloud4.team4.travelog.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreateRequestDto {
    private String title;
    private String region;

    @Builder
    public BoardCreateRequestDto(String title, String region) {
        this.title = title;
        this.region = region;
    }
}
