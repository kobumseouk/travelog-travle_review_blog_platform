package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.board.dto.BoardDescRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardRequestDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-30T19:51:15+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
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
        board.regionMiddle( dto.getRegionMiddle() );
        board.description( dto.getDescription() );
        board.boardCategory( dto.getBoardCategory() );

        return board.build();
    }

    @Override
    public void toUpdateEntity(BoardDescRequestDto dto, Board entity) {
        if ( dto == null ) {
            return;
        }
    }
}
