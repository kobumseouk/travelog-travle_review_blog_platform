package cloud4.team4.travelog.domain.member.service;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.dto.MemberMapper;
import cloud4.team4.travelog.domain.member.entity.MemberEntity;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(MemberDto memberDto){


    }
    public MemberDto findMember(Long memberId){
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
        MemberDto memberDto = MemberMapper.toMemberDto(memberEntity.get());
        return memberDto;
    }



}
