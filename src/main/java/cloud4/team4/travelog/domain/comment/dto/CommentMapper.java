package cloud4.team4.travelog.domain.comment.dto;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.comment.entity.ExMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "member", target="memberId")
    CommentResponseDto toResponseDto(Comment comment);

    default String mapMemberToString(ExMember member) {
        return member != null ? member.getId() : null;
    }

    @Mapping(target = "member", ignore = true) // member 필드 매핑 제외
    Comment toEntity(CommentRequestDto commentRequestDto);
}
