package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.LikesService;
import com.estsoft.paldotourism.service.UserDetailService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikesService likesService;
    private final UserDetailService userDetailService;

    @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> addLike(@RequestParam("busId") Long busId) {
        Optional<User> currentUser = userDetailService.getCurrentUser();
        if(currentUser.isEmpty()) {
            throw new IllegalArgumentException("로그인하지않은유저입니다.");
        }

        likesService.addLike(currentUser.get(),busId);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
    @DeleteMapping ("")
    public ResponseEntity<?> cancelLike(@RequestParam("busId") Long busId) {
        Optional<User> currentUser = userDetailService.getCurrentUser();
        if(currentUser.isEmpty()) {
            throw new IllegalArgumentException("로그인하지않은유저입니다.");
        }

        likesService.cancelLike(currentUser.get(),busId);

        return ResponseEntity.ok().build();
    }
}
