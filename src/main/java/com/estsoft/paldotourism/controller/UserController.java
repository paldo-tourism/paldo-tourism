package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.user.AddUserRequestDto;
import com.estsoft.paldotourism.dto.user.AddUserResponseDto;
import com.estsoft.paldotourism.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // User 회원가입 api
    @PostMapping("/api/signup")
    public AddUserResponseDto signUp(@RequestBody AddUserRequestDto requestDto) {
        return userService.saveUser(requestDto);
    }
}
