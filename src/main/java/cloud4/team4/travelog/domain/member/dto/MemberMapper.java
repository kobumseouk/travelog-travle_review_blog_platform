package cloud4.team4.travelog.domain.member.dto;

import cloud4.team4.travelog.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    // entity를 dto로 매핑
    MemberDto toMemberDto(Member member);

    // dto를 entity로 매핑
    Member toMemberEntity(MemberDto memberDto);
}