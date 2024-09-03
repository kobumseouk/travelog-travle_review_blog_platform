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
import org.springframework.ui.Model;

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
            throw new IllegalArgumentException("동일한 id로 이미 가입되어있습니다.");
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
            MemberDto memberDto = MemberMapper.INSTANCE.toMemberDto(memberentity.get());
            session.setAttribute("member", memberDto); // MemberDto 객체를 세션에 저장
            session.setAttribute("status", memberDto.getStatus());
            return memberDto;

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
        member.setPassword(memberDto.getPassword());
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

    //비밀번호 찾기

    public String findPassword(String loginId, String name, String email, String phoneNumber, Model model) {
        Optional<Member> memberEntity = memberRepository.findByLoginIdAndNameAndEmailAndPhoneNumber(loginId, name, email, phoneNumber);
        if (memberEntity.isPresent()) {
            model.addAttribute("loginId", loginId);
            return "redirect:/resetPassword?loginId=" + loginId; // 비밀번호 재설정 페이지로 리다이렉트
        } else {
            model.addAttribute("error", "입력한 정보가 일치하지 않습니다.");
            return "findPassword";
        }
    }

    // 새로운 비밀번호로 업데이트
    public void resetPassword(String loginId, String newPassword) {
        Optional<Member> memberEntity = memberRepository.findByLoginId(loginId);
        if (memberEntity.isPresent()) {
            Member member = memberEntity.get();
            member.setPassword(passwordEncoder.encode(newPassword));
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        }
    }



}
