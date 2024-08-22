package cloud4.team4.travelog.domain.member.service;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.dto.MemberMapper0;
import cloud4.team4.travelog.domain.member.entity.MemberEntity;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
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
    // 시큐리티 설정할때 BCryptPasswordEncoder 추가해야함
    public String encodingPassword(String password){
        String encodedPassword = passwordEncoder.encode(password);
        return encodedPassword;
    }

    // 회원가입
    public void createMember(MemberDto memberDto){
        //Dto를 entity로 매핑한 것을 가져옴
        MemberEntity memberEntity = MemberMapper0.toMemberEntity(memberDto);

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
        MemberDto memberDto = MemberMapper0.toMemberDto(memberEntity.get());
        return memberDto;
    }

    //업데이트
    public void  updateMember(Long memberId, MemberDto memberDto) {
        MemberEntity memberEntity = memberRepository.findById(memberId).get();
        memberEntity.setName(memberDto.getName());
        memberEntity.setEmail(memberDto.getEmail());
        memberEntity.setPhoneNumber(memberDto.getPhoneNumber());

        memberRepository.save(memberEntity);
    }

    //회원삭제
    public void deleteMember(Long memberId){
        MemberEntity memberEntity = memberRepository.findById(memberId).get();
        memberRepository.delete(memberEntity);

    }




    //아이디찾기 (이름&이메일방식)
    public String findMemberIdByEmail(String name, String email){
        Optional<MemberEntity> member = memberRepository.findByNameAndEmail(name, email);
        if(member.isPresent()) {
            return member.get().getLoginId();
        }
        else{
          return ("존재하지 않는 아이디입니다.");
        }
    }

    //아이디찾기 (이름&전화번호 방식)
    public String findMemberIdByNum(String name, String phoneNumber){
        Optional<MemberEntity> member = memberRepository.findByNameAndNum(name, phoneNumber);
        if(member.isPresent()) {
            return member.get().getLoginId();
        }
        else{
            return ("존재하지 않는 아이디입니다..");
        }
    }








}
