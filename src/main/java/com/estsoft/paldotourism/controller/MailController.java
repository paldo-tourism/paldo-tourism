package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.MailDto;
import com.estsoft.paldotourism.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail")
    public void execMail(@RequestParam String reservationNumber) {
        MailDto mailDto = mailService.setMailDto(reservationNumber);
        mailService.mailSend(mailDto);
    }
}
