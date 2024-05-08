package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import com.estsoft.paldotourism.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserPageController {

    private final ReservationService reservationService;

    // 회원가입 뷰
    @GetMapping("/signup")
    public String signUp(Model model) {
        AddUserRequestDto user = new AddUserRequestDto();
        model.addAttribute("user", user);
        return "user/signup";
    }

    // 로그인 뷰
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    // 로그인 결과 확인 테스트 페이지
    @GetMapping("/login-result-test")
    public String loginResultTest(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        model.addAttribute("currentUser", currentUser);
        return "user/login-result-test";
    }

    // 비밀번호 찾기 뷰
    @GetMapping("/forgot-pw")
    public String forgotPw() {
        return "user/forgot-pw";
    }

    /*
    // 마이페이지 뷰
    @GetMapping("/myPage")
    public String myPage(Model model, @AuthenticationPrincipal UserDetails currentLoginUser) {
        model.addAttribute("currentLoginUser", currentLoginUser);
        return "user/myPage/myPage-main";
    }
     */
    // 마이페이지 뷰(메인 - 예매내역)
    @GetMapping("/myPage")
    public String myPage() {
        return "user/myPage/myPage-main";
    }

    // 마이페이지 - 회원정보 수정 / 탈퇴 뷰
    @GetMapping("/myPage/edit")
    public String myPageEdit() {
        return "user/myPage/myPage-edit";
    }

    // 마이페이지 - 찜
    @GetMapping("/myPage/likes")
    public String myPageLikes() {
        return "user/myPage/myPage-likes";
    }
}
