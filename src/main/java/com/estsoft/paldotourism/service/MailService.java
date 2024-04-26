package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.MailDto;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "naverkakao0622@gmail.com";
    private static final String TITLE = "paldo-tourism에서 예약하신 버스예약 정보 입니다 !";

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(MailService.TITLE);
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
