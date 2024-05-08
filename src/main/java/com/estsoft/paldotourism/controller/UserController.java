package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import com.estsoft.paldotourism.dto.user.AddUserResponseDto;
import com.estsoft.paldotourism.dto.user.PasswordChangeDto;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.MailService;
import com.estsoft.paldotourism.service.UserDetailService;
import com.estsoft.paldotourism.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;
    private final UserDetailService userDetailService;

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
    @PostMapping("/api/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody Map<String, String> credentials,
                                           HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String password = credentials.get("password");

        boolean passwordMatch = userService.checkPassword(user, password);
        if(passwordMatch) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            userService.deleteUser(user);
            return ResponseEntity.ok(Map.of("success", true, "message", "계정이 성공적으로 삭제되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", "비밀번호가 잘못되었습니다."));
        }
    }

    @GetMapping("/api/auth/status")
    public ResponseEntity<?> getUserAuthStatus() {
        Optional<User> user = userDetailService.getCurrentUser();
        if(user.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}