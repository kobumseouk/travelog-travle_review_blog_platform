package cloud4.team4.travelog.domain.member.controller;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequestMapping("/api")
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
    public ResponseEntity<MemberDto> findUser(@PathVariable Long memberId){
        return ResponseEntity.ok(memberService.findMember(memberId));

    }

    // 업데이트
    @PutMapping("/members/{memberId}")
    public ResponseEntity<String> updateMember(@PathVariable Long memberId, @RequestBody MemberDto memberDto) {
        memberService.updateMember(memberId, memberDto);
        return ResponseEntity.ok("회원 정보 수정 성공");
    }

    //회원삭제
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("회원 삭제 성공");
    }


    //아이디 찾기 (이름&이메일방식)
    @GetMapping("/find-id-by-email")
    public ResponseEntity<String> findMemberIdByEmail(@RequestParam String name, @RequestParam String email) {
        return ResponseEntity.ok(memberService.findMemberIdByEmail(name, email));

    }
    //아이디 찾기 (이름&전화번호 방식)
//    @GetMapping("/find-id-by-phone")
//    public ResponseEntity<String> findMemberIdByNum(@RequestParam String name, @RequestParam String phoneNumber) {
//        return ResponseEntity.ok(memberService.findMemberIdByNum(name,phoneNumber));
//
//    }

}
