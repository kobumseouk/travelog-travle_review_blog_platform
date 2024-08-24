package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.board.dto.BoardCreateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateRequestDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-24T15:43:01+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board toEntity(BoardCreateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Board.BoardBuilder board = Board.builder();

        board.regionMajor( dto.getRegionMajor() );
        board.regionMiddle( dto.getRegionMiddle() );
        board.description( dto.getDescription() );

        return board.build();
    }

    @Override
    public BoardUpdateRequestDto toUpdateRequestDto(Board board) {
        if ( board == null ) {
            return null;
        }

        Board board1 = null;

        board1 = board;

        BoardUpdateRequestDto boardUpdateRequestDto = new BoardUpdateRequestDto( board1 );

        return boardUpdateRequestDto;
    }

    @Override
    public void toUpdateEntity(BoardUpdateRequestDto dto, Board entity) {
        if ( dto == null ) {
            return;
        }
    }
}
