package cloud4.team4.travelog.domain.member.dto;

import cloud4.team4.travelog.domain.member.entity.Member;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-24T17:14:18+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto toMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setId( member.getId() );
        memberDto.setName( member.getName() );
        memberDto.setPhoneNumber( member.getPhoneNumber() );
        memberDto.setLoginId( member.getLoginId() );
        memberDto.setPassword( member.getPassword() );
        memberDto.setEmail( member.getEmail() );

        return memberDto;
    }

    @Override
    public Member toMemberEntity(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        Member member = new Member();

        member.setId( memberDto.getId() );
        member.setLoginId( memberDto.getLoginId() );
        member.setName( memberDto.getName() );
        member.setPassword( memberDto.getPassword() );
        member.setPhoneNumber( memberDto.getPhoneNumber() );
        member.setEmail( memberDto.getEmail() );

        return member;
    }
}
