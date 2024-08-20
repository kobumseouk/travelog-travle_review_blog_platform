package cloud4.team4.travelog.domain.member.dto;


import cloud4.team4.travelog.domain.member.entity.MemberEntity;

public class MemberMapper {
    public static MemberDto toMemberDto(MemberEntity memberEntity){
        MemberDto memberDto = new MemberDto();

        memberDto.setId(memberEntity.getId());
        memberDto.setName(memberDto.getName());
        memberDto.setPassword(memberDto.getPassword());
        memberDto.setPhoneNumber(memberDto.getPhoneNumber());
        return memberDto;

    }

}
