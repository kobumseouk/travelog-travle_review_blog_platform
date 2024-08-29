package cloud4.team4.travelog.domain.member.controller;

import cloud4.team4.travelog.domain.member.dto.MemberDto;
import cloud4.team4.travelog.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            return "redirect:/"; // 로그인 성공시 홈페이지 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
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
    public String findPassword(@RequestParam("loginId") String loginId, @RequestParam("name") String name,
                               @RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber, Model model) {
        return memberService.findPassword(loginId, name, email, phoneNumber, model);
    }
    @GetMapping("/resetPassword")
    public String resetPasswordForm(@RequestParam("loginId") String loginId, Model model) {
        model.addAttribute("loginId", loginId);
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("loginId") String loginId, @RequestParam("newPassword") String newPassword) {
        memberService.resetPassword(loginId, newPassword);
        return "redirect:/login"; // 비밀번호 재설정 후 로그인 페이지로 리다이렉트
    }

    //마이페이지
    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model) {
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        model.addAttribute("member", memberDto);
        return "mypage";
    }

    //마이페이지 수정
    @GetMapping("/mypage/edit")
    public String editMyPage(HttpSession session, Model model) {
        MemberDto memberDto = (MemberDto) session.getAttribute("member");
        model.addAttribute("member", memberDto);
        return "mypageEdit";
    }

    // 마이페이지 수정 동작
    @PostMapping("/mypage/edit")
    public String updateMyPage(@ModelAttribute MemberDto memberDto, HttpSession session, Model model) {
        try {
            MemberDto sessionMember = (MemberDto) session.getAttribute("member");
            memberDto.setId(sessionMember.getId()); // 세션에서 id 값을 가져와서 설정
            memberDto.setLoginId(sessionMember.getLoginId()); // 세션에서 로그인 아이디를 가져와서 설정

            // 비밀번호가 비어 있으면 기존 비밀번호를 유지
            if (memberDto.getPassword() == null || memberDto.getPassword().isEmpty()) {
                memberDto.setPassword(sessionMember.getPassword());
            } else {
                memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
            }

            memberService.updateMember(sessionMember.getId(), memberDto);
            session.setAttribute("member", memberDto); // 세션 업데이트
            return "redirect:/mypage"; // 수정 후 마이페이지로 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "mypageEdit";
        }
    }



    // 회원 삭제 동작
    @PostMapping("/mypage/delete")
    public String deleteMember(HttpSession session, Model model) {
        try {
            MemberDto memberDto = (MemberDto) session.getAttribute("member");
            memberService.deleteMember(memberDto.getId());
            session.invalidate(); // 세션 무효화
            return "redirect:/"; //
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "mypageEdit";
        }
    }
}
