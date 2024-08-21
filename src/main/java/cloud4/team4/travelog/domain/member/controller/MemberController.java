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




}
