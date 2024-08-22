package cloud4.team4.travelog.domain.member.dto;


import cloud4.team4.travelog.domain.member.entity.Member;

// entity를 dto로 매핑
public class MemberMapper0 {
    public static MemberDto toMemberDto(Member member){
        MemberDto memberDto = new MemberDto();

        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setPhoneNumber(member.getPhoneNumber());
        memberDto.setLoginId(member.getLoginId());
        memberDto.setPassword(member.getPassword());

        return memberDto;

    }

    // dto를 entity로 매핑
    public static Member toMemberEntity(MemberDto memberDto){

        Member member = new Member();

        member.setName(memberDto.getName());
        member.setPhoneNumber(memberDto.getPhoneNumber());
        member.setLoginId(memberDto.getLoginId());
        member.setPassword(memberDto.getPassword());

        return member;
    }

}
