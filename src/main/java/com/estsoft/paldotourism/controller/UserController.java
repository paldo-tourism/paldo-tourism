package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.MailDto;
import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import com.estsoft.paldotourism.dto.user.AddUserResponseDto;
import com.estsoft.paldotourism.dto.user.PasswordChangeDto;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.MailService;
import com.estsoft.paldotourism.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    // User 회원가입 api
    @PostMapping("/api/signup")
    public AddUserResponseDto signUp(@RequestBody AddUserRequestDto requestDto) {
        return userService.saveUser(requestDto);
    }

    // 이메일 중복 확인 api
    @GetMapping("/api/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestParam String email) {
        boolean isAvailable = userService.isEmailAvailable(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    // 닉네임 중복 확인 api
    @GetMapping("/api/check-nickname")
    public boolean checkNickname(@RequestParam String nickname) {
        return userService.isNicknameAvailable(nickname);
    }

    // 비밀번호 찾기 api
    @PostMapping("/api/forgot-pw")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email,
                                           @RequestParam("phoneNumber") String phoneNumber) {
        String tempPwd = userService.resetPassword(email, phoneNumber);

        mailService.sendTempPwd(email, tempPwd);
        return ResponseEntity.ok("임시 비밀번호가 발급되었습니다.");

    }

    // 비밀번호 변경 api
    @PostMapping("/api/change-pw")
    public ResponseEntity<?> changePassword(HttpServletRequest request, HttpServletResponse response,
                                            @RequestBody PasswordChangeDto passwordChangeDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        boolean success = userService.changePassword(user, passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());

        if (success) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return ResponseEntity.ok(Map.of("success", true, "message", "비밀번호가 성공적으로 변경되었습니다."));
        } else {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "비밀번호 변경에 실패했습니다. 현재 비밀번호를 확인해주세요."));
        }
    }

    // 회원 탈퇴 api
}
