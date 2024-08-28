package cloud4.team4.travelog.domain.comment.dto;

import cloud4.team4.travelog.domain.comment.entity.Reply;
import cloud4.team4.travelog.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReplyMapper {

    ReplyMapper INSTANCE = Mappers.getMapper(ReplyMapper.class);

    @Mapping(source = "member", target="nickname")
    @Mapping(source = "member", target="memberId")
    @Mapping(source = "id", target = "id")
    ReplyResponseDto toResponseDto(Reply reply);


    default Long mapMemberToLong(Member member) {
        return member != null ? member.getId() : null;
    }
    default String mapMemberToString(Member member) {
        return member != null ? member.getName() : null;
    }

    @Mapping(target = "member", ignore = true) // member 필드 매핑 제외
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // 현재 시각으로 createdAt 매핑
    Reply toEntity(ReplyRequestDto replyRequestDto);
}
