package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.board.dto.BoardCreateRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    // @Mapping(source = "board", target = "post")

    /*---------- Create 매핑 ----------*/

    // CreateRequestDto -> Board 매핑
    @Mapping(target = "id", ignore = true) // ID는 자동 생성되므로 무시
    Board toEntity(BoardCreateRequestDto dto);

    /*---------- Update 매핑 ----------*/

    // Board -> UpdateRequestDto 매핑
    BoardUpdateRequestDto toUpdateRequestDto(Board board);

    // UpdateRequestDto -> Board 매핑
    @Mapping(target = "id", ignore = true)
    void toUpdateEntity(BoardUpdateRequestDto dto, @MappingTarget Board entity);




}
