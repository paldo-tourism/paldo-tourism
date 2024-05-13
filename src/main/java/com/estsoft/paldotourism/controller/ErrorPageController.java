package com.estsoft.paldotourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/404error")
    public String error() {
        return "error/404error";
    }
}
