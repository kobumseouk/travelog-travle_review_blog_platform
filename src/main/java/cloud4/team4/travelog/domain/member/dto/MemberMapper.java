package cloud4.team4.travelog.domain.member.dto;


import cloud4.team4.travelog.domain.member.entity.MemberEntity;

// entity를 dto로 매핑
public class MemberMapper {
    public static MemberDto toMemberDto(MemberEntity memberEntity){
        MemberDto memberDto = new MemberDto();

        memberDto.setId(memberEntity.getId());
        memberDto.setName(memberEntity.getName());
        memberDto.setPhoneNumber(memberEntity.getPhoneNumber());
        memberDto.setLoginId(memberEntity.getLoginId());
        memberDto.setPassword(memberEntity.getPassword());

        return memberDto;

    }

    // dto를 entity로 매핑
    public static MemberEntity toMemberEntity(MemberDto memberDto){

        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setName(memberDto.getName());
        memberEntity.setPhoneNumber(memberDto.getPhoneNumber());
        memberEntity.setLoginId(memberDto.getLoginId());
        memberEntity.setPassword(memberDto.getPassword());

        return memberEntity;
    }

}
