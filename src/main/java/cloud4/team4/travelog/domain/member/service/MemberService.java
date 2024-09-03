package cloud4.team4.travelog.domain.member.service;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.dto.MemberMapper;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
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

//     패스워드 인코딩
// AppConfig에 BCryptPasswordEncoder만 추가함

    public String encodingPassword(String password){
        return passwordEncoder.encode(password);
    }

    // 회원가입
    public void createMember(MemberDto memberDto){
        //Dto를 entity로 매핑한 것을 가져옴
        Member memberEntity = MemberMapper.INSTANCE.toMemberEntity(memberDto);

        //중복처리
        Optional<Member> member = memberRepository.findByLoginId(memberEntity.getLoginId());
        if(member.isPresent()){
            throw new IllegalArgumentException("이미 있는 회원입니다.");
        }
        //회원가입할때 기본적으로 일반 사용자로 설정
        memberEntity.setStatus("USER");

        // 인코딩된 패스워드 저장 및 회원 저장( 해시저장 )
        memberEntity.setPassword(encodingPassword(memberEntity.getPassword()));
        memberRepository.save(memberEntity);

    }

    //로그인
    public MemberDto login(String loginId, String password, HttpSession session) {
        Optional<Member> memberentity = memberRepository.findByLoginId(loginId);

        //회원 정보 존재 & 저장된 해쉬비밀번호와 일치하는지 확인
        if(memberentity.isPresent() && passwordEncoder.matches(password, memberentity.get().getPassword())) {
            //세선에 회원 정보와 상태 저장
            session.setAttribute("member", memberentity.get());
            session.setAttribute("status", memberentity.get().getStatus());

            //회원 정보 DTO로 변환하여 반환
            return MemberMapper.INSTANCE.toMemberDto(memberentity.get());
        } else {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }

    // 로그아웃
    public void logout(HttpSession session) {

        session.invalidate();
    }


    //단일처리
    public MemberDto findMember(Long memberId){
        Member member = memberRepository.findById(memberId).get();
        return MemberMapper.INSTANCE.toMemberDto(member);
    }

    //업데이트
    public void updateMember(Long memberId, MemberDto memberDto) {
        Member member = memberRepository.findById(memberId).get();
        member.setName(memberDto.getName());
        member.setEmail(memberDto.getEmail());
        member.setPhoneNumber(memberDto.getPhoneNumber());
        memberRepository.save(member);
    }

    //회원삭제
    public void deleteMember(Long memberId){
        Member member = memberRepository.findById(memberId).get();
        memberRepository.delete(member);

    }


    //아이디찾기 (이름&이메일방식)
    public String findMemberIdByEmail(String name, String email){
        Optional<Member> memberEntity = memberRepository.findByNameAndEmail(name, email);
        if(memberEntity.isPresent()) {
            return memberEntity.get().getLoginId();
        }
        else{
          return ("존재하지 않는 아이디입니다.");
        }
    }

//    //아이디찾기 (이름&전화번호 방식)
//    public String findMemberIdByNum(String name, String phoneNumber){
//        Optional<Member> memberEntity = memberRepository.findByNameAndPhoneNumber(name, phoneNumber);
//        if(memberEntity.isPresent()) {
//            return memberEntity.get().getLoginId();
//        }
//        else{
//            return ("존재하지 않는 아이디입니다..");
//        }
//    }

    //비밀번호 찾기

    public String findPassword(String loginId, String name, String email, String phoneNumber) {
        // 로그인  id로 회원정보 조회
        Optional<Member> memberEntity = memberRepository.findByLoginIdAndNameAndEmailAndPhoneNumber(loginId, name, email, phoneNumber);
        if (memberEntity.isPresent()) {
            return memberEntity.get().getPassword();
        } else {
            return ("다시 확인해주세요.");
        }
    }

    // 비밀번호 변경

//
//        }
//
//
//        //
//    }


}
