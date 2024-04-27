package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signUp(Model model) {
        AddUserRequestDto user = new AddUserRequestDto();
        model.addAttribute("user", user);
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    // 로그인 결과 확인 테스트 페이지
    @GetMapping("/login-result-test")
    public String loginResultTest(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userDetails);
        return "user/login-result-test";
    }

    @GetMapping("/logout")
    public String logout() {
        return "user/login";
    }
}
