package com.estsoft.paldotourism.dto;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

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

    //아래 항목 테스트 변수로 삭제 예정입니다:)
    private String address;
    private String title;

//    public MailDto(Bus reservationBus) {
//        this.dep_terminal = reservationBus.dep_terminal;
//        this.arr_terminal = reservationBus.arr_terminal;
//        this.dep_time = reservationBus.dep_time;
//        this.arr_time = reservationBus.arr_time;
//        this.charge = reservationBus.charge;
//        this.bus_grade = reservationBus.busgrade;
//    }
//    public MailDto(Reservation reservation) {
//        this.reservationNumber = reservation.reservationNumber;
//    }
//    public MailDto(User user) { //매개변수 변경이 필요합니다.
//        this.email = user.getEmail();
//    }

    public String makeMessageDump(){
        this.message = this.nickname + "님의 예약 내역입니다."
                + dep_terminal + "터미널 에서 "
                + dep_time + "에 출발하여"
                + arr_terminal + "터미널에 "
                + arr_time + "에 도착하는 "
                + bus_grade + "등급 버스를 예매 하셨습니다."
                + charge + "원 결제 하셨으며,"
                + "예약번호는 " + reservationNumber + "입니다.";

        return message;
    }
}
