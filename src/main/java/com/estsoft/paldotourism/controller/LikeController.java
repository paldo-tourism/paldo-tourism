package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikesService likesService;

//    @PreAuthorize()
//    @PostMapping("")
//    public ResponseEntity<?> addLike(@RequestParam("busId") Long busId) {
//
//    }
}
