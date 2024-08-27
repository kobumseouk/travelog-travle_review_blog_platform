package cloud4.team4.travelog.domain.member.controller;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TestController {
    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signUpPage() {
        return "signup";
    }

    //회원가입 동작
    @PostMapping("/signup")
    public String createMember(@ModelAttribute MemberDto memberDto, Model model) {
        try {
            memberService.createMember(memberDto);
            return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }


    // 로그인 페이지
    @GetMapping("/login")
    public String LoginPage() {
        return "login";
    }

    //로그인 동작
    @PostMapping("/login")
    public String login(@RequestParam("loginId") String loginId, @RequestParam("password") String password, HttpSession session, Model model) {
        try {
            MemberDto memberDto = memberService.login(loginId, password, session);
            model.addAttribute("member", memberDto);
            return "redirect:/home"; // 로그인 성공시 홈페이지 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("로그인실패", e.getMessage());
            return "login"; //
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        memberService.logout(session);
        return "redirect:/login";
    }

    //아이디 찾기
    @GetMapping("/findId")
    public String findIdPage() {
        return "findId";
    }

    //아이디찾기결과
    @PostMapping("/findId")
    public String findId(@RequestParam("name") String name, @RequestParam("email") String email, Model model) {
        String loginId = memberService.findMemberIdByEmail(name, email);
        model.addAttribute("loginId", loginId);
        return "findIdResult";
    }

    //비밀번호 찾기
    @GetMapping("/findPassword")
    public String findPasswordPage() {
        return "findPassword";
    }

    //비밀번호 찾기 결과
    @PostMapping("/findPassword")
    public String findPassword(@RequestParam("loginId") String loginId, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber, Model model) {
        String password = memberService.findPassword(loginId, name, email, phoneNumber);
        model.addAttribute("password", password);
        return "findPasswordResult";
    }






}
