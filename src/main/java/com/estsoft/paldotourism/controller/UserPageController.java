package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserPageController {

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

    // 헤더 테스트 뷰
    // 로그인 후 헤더에 유저 정보 있는지 확인용
    @GetMapping("/header-test")
    public String headerTest(@AuthenticationPrincipal UserDetails currentLoginUser, Model model) {
        model.addAttribute("currentLoginUser", currentLoginUser);
        return "layout/header";
    }

    // 비밀번호 찾기 뷰
    @GetMapping("/forgot-pw")
    public String forgotPw() {
        return "user/forgot-pw";
    }

    // 마이페이지 뷰
    @GetMapping("/myPage")
    public String myPage() {
        return "user/myPage/myPage-main";
    }

    // 마이페이지 - 회원정보 수정 / 탈퇴 뷰
    @GetMapping("/myPage/edit")
    public String myPageEdit() {
        return "user/myPage/myPage-edit";
    }
}
