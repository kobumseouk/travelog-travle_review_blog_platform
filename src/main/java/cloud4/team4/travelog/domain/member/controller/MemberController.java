package cloud4.team4.travelog.domain.member.controller;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<String> createMember(@RequestBody MemberDto memberDto){
        memberService.createMember(memberDto);
        return ResponseEntity.ok("회원가입 성공");
    }


    // 단건조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberDto> findUser(@PathVariable(name = "memberId") Long memberId){
        log.info("{}",memberId);
        return ResponseEntity.ok(memberService.findMember(memberId));
    }

    // 업데이트
    @PutMapping("/members/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable(name = "memberId") Long memberId, @RequestBody MemberDto memberDto) {
        memberService.updateMember(memberId, memberDto);
        return ResponseEntity.ok("회원 정보 수정 성공");
    }

    //회원삭제
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable(name = "memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("회원 삭제 성공");
    }


    //로그인
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestParam("loginId") String loginId, @RequestParam("password") String password, HttpSession session) {
        MemberDto memberDto = memberService.login(loginId, password, session);
        return ResponseEntity.ok(memberDto);
    }


    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        memberService.logout(session);
        return ResponseEntity.ok("로그아웃 성공");
    }

    // 관리자 관리 페이지 개발중
    @GetMapping("/admin")
    public ResponseEntity<String> adminPage(HttpSession session) {
        String status = (String) session.getAttribute("status");
        if ("ADMIN".equals(status)) {
            return ResponseEntity.ok("관리자 페이지에 접근하셨습니다.");
        } else {
            return ResponseEntity.status(403).body("접근 권한이 없습니다.");
        }
    }

    // 마이페이지 조회 (로그인한 유저의 정보)
    @GetMapping("/my-page")
    public ResponseEntity<MemberDto> getMyPage(@RequestParam Long memberId) {
        return ResponseEntity.ok(memberService.findMember(memberId));
    }


    //아이디 찾기 (이름&이메일방식)
    @GetMapping("/find-id-by-email")
    public ResponseEntity<String> findMemberIdByEmail(@RequestParam String name, @RequestParam String email) {
        return ResponseEntity.ok(memberService.findMemberIdByEmail(name, email));

    }



}
