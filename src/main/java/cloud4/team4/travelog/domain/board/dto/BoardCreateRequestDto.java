package cloud4.team4.travelog.domain.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BoardCreateRequestDto {
    private String regionMajor;
    private String regionMiddle;
    private String description;


    public BoardCreateRequestDto(String regionMajor, String regionMiddle, String description) {
        this.regionMajor = regionMajor;
        this.regionMiddle = regionMiddle;
        this.description = description;
    }
}
