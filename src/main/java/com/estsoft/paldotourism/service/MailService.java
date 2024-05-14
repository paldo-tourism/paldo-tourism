package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.MailDto;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ReservationRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender mailSender;

    private ReservationRepository reservationRepository;

    private SeatRepository seatRepository;
    private static final String FROM_ADDRESS = "naverkakao0622@gmail.com";
    private static final String TITLE = "paldo-tourism에서 예약하신 버스예약 정보 입니다 !";


    public MailDto setMailDto(String reservationNumber) {
        MailDto mailDto = MailDto.builder().build();
        StringBuilder seat = new StringBuilder();

        String busInfo = reservationRepository.findBusDetails(reservationNumber).get(0);
        List<String> busInfoSeperated = Arrays.asList(busInfo.split(","));

        List<Seat> seatInfo = seatRepository.findByReservationNumber(reservationNumber);
        int seatCount = seatInfo.size();
        makeSeatList(seatInfo, seat);

        mailDto.setSeatNumber(seat.toString());
        mailDto.setDep_terminal(busInfoSeperated.get(0));
        mailDto.setArr_terminal(busInfoSeperated.get(1));
        mailDto.setDep_time(timeFormat(busInfoSeperated.get(2)));
        mailDto.setArr_time(timeFormat(busInfoSeperated.get(3)));
        mailDto.setCharge(Integer.parseInt(busInfoSeperated.get(4)) * seatCount);
        mailDto.setBus_grade(busInfoSeperated.get(5));
        mailDto.setReservationNumber(reservationNumber);
        mailDto.setNickname(getAuthenticatedUser().getNickName());
        mailDto.setEmail(getAuthenticatedUser().getEmail());

        return mailDto;
    }

    private static void makeSeatList(List<Seat> seatInfo, StringBuilder seat) {
        for (int i = 0; i < seatInfo.size(); i++) {
            if (i == seatInfo.size() - 1) {
                seat.append(seatInfo.get(i).getSeatNumber());
            } else {
                seat.append(seatInfo.get(i).getSeatNumber()).append(", ");
            }
        }
    }

    private String timeFormat(String time){
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String hour = time.substring(8, 10);
        String minute = time.substring(10, 12);

        time = year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분";

        return time;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public void mailSend(MailDto mailDto) {
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
