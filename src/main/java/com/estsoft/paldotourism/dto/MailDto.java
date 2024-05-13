package com.estsoft.paldotourism.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class MailDto {
    private String dep_terminal;
    private String arr_terminal;
    private String dep_time;
    private String arr_time;
    private int charge;
    private String bus_grade;
    private String message;
    private String email;
    private String nickname;
    private String reservationNumber;
    private String seatNumber;


    public String makeMessageDump(){
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(this.nickname)
                .append("님의 예약 내역입니다.\n")
                .append(dep_terminal).append("터미널에서 ")
                .append(dep_time).append("에 출발하여 ")
                .append(arr_terminal).append("터미널에 ")
                .append(arr_time).append("에 도착하는 ")
                .append(bus_grade).append("등급 버스를 예매 하셨습니다. \n")
                .append(charge).append("원 결제 하셨으며, 좌석번호는 ")
                .append(seatNumber)
                .append(" 예약번호는 ").append(reservationNumber).append("입니다.\n")
                .append("즐거운 여행 되세요 :)");

        return this.message = messageBuilder.toString();
    }
}
