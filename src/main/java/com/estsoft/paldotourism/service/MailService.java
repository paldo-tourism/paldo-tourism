package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.MailDto;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ReservationRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender mailSender;

    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private static final String FROM_ADDRESS = "naverkakao0622@gmail.com";
    private static final String TITLE = "paldo-tourism에서 예약하신 버스예약 정보 입니다 !";

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public MailDto setMailDto(){
        MailDto mailDto = MailDto.builder().build();


        Object[] userDetails = reservationRepository.findUserDetails();
        Object[] busDetails = reservationRepository.findBusDetails();
        String seatNumber = reservationRepository.findSeatDetails();
        String reservationNumber = reservationRepository.findReservationDetails();

        mailDto.setNickname(getAuthenticatedUser().getNickName());
        mailDto.setEmail(getAuthenticatedUser().getEmail());
//        mailDto.setNickname((String) userDetails[0]);
//        mailDto.setEmail((String) userDetails[1]);
        mailDto.setDep_terminal((String) busDetails[0]);
        mailDto.setArr_terminal((String) busDetails[1]);
        mailDto.setDep_time((String) busDetails[2]);
        mailDto.setArr_time((String) busDetails[3]);
        mailDto.setCharge((Integer) busDetails[4]);
        mailDto.setBus_grade((String) busDetails[5]);
        mailDto.setSeatNumber(seatNumber);
        mailDto.setReservationNumber(reservationNumber);

        return mailDto;
    }

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailDto.getEmail());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(MailService.TITLE);
        message.setText(mailDto.makeMessageDump());
        mailSender.send(message);
    }

    // 비밀번호 찾기 메일
    public void sendTempPwd(String to, String tempPwd) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject("8도 관광 - 비밀번호 찾기");
        message.setText("임시 비밀번호는 " + tempPwd + " 입니다.");
        mailSender.send(message);
    }
}
