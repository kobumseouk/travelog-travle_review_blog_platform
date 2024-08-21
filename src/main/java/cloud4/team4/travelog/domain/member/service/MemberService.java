package cloud4.team4.travelog.domain.member.service;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.dto.MemberMapper;
import cloud4.team4.travelog.domain.member.entity.MemberEntity;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@RequiredArgsConstructor
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 패스워드 인코딩
    public String encodingPassword(String password){
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }

    // 회원가입
    public void createMember(MemberDto memberDto){
        //Dto를 entity로 매핑한 것을 가져옴
        MemberEntity memberEntity = MemberMapper.toMemberEntity(memberDto);

        //중복처리
        Optional<MemberEntity> member = memberRepository.findByLoginId(memberEntity.getLoginId());
        if(member.isPresent()){
            throw new IllegalArgumentException("이미 있는 회원입니다.");
        }

        // 인코딩된 패스워드 저장 및 회원 저장( 해시저장 )
        memberEntity.setPassword(encodingPassword(memberEntity.getPassword()));
        memberRepository.save(memberEntity);

    }

    //단일처리
    public MemberDto findMember(Long memberId){
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
        MemberDto memberDto = MemberMapper.toMemberDto(memberEntity.get());
        return memberDto;
    }



}
