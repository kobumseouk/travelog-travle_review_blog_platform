package cloud4.team4.travelog.domain.board.entity;

import cloud4.team4.travelog.domain.board.dto.BoardDescRequestDto;
import cloud4.team4.travelog.domain.board.dto.BoardRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    // CreateRequestDto -> Board 매핑
    // 오류 발생으로 명시적 매핑을 수행했다.
    @Mapping(target = "id", ignore = true) // ID는 자동 생성되므로 무시
    @Mapping(target = "regionMajor", source = "regionMajor")
    @Mapping(target = "regionMiddle", source = "regionMiddle")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "boardCategory", source = "boardCategory")
    Board toEntity(BoardRequestDto dto);

    /*---------- Update 매핑 ----------*/

    // 미구현: Board -> UpdateRequestDto 매핑
//    BoardDescRequestDto toUpdateRequestDto(Board board);


    // UpdateRequestDto -> Board 매핑
    @Mapping(target = "id", ignore = true)
    void toUpdateEntity(BoardDescRequestDto dto, @MappingTarget Board entity);

}
