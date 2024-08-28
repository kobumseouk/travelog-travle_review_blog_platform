package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.board.dto.BoardDescRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardRequestDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-28T21:07:20+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board toEntity(BoardRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Board.BoardBuilder board = Board.builder();

        board.regionMajor( dto.getRegionMajor() );
        board.description( dto.getDescription() );
        board.boardCategory( dto.getBoardCategory() );

        return board.build();
    }

    @Override
    public BoardDescRequestDto toUpdateRequestDto(Board board) {
        if ( board == null ) {
            return null;
        }

        BoardDescRequestDto.BoardDescRequestDtoBuilder boardDescRequestDto = BoardDescRequestDto.builder();

        boardDescRequestDto.description( board.getDescription() );

        return boardDescRequestDto.build();
    }

    @Override
    public void toUpdateEntity(BoardDescRequestDto dto, Board entity) {
        if ( dto == null ) {
            return;
        }
    }
}
