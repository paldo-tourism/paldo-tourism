package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.AlanDto;
import com.estsoft.paldotourism.service.AlanService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;


@Controller
public class AlanController {
    private final AlanService alanService;

    public AlanController(AlanService alanService) {
        this.alanService = alanService;
    }

    @PostMapping("/api/ai/aiRecommend")
    public String processAlanForm(@ModelAttribute AlanDto alanDto, Model model){
        String aiResponse = alanService.askAi(alanDto);

        model.addAttribute("response",aiResponse);
        return "/ai/aiAnswer";
    }

    @GetMapping("/main")
    public String goMain() {
        return "/index";
    }
}
