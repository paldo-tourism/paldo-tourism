package com.estsoft.paldotourism.dto.reservation;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static com.estsoft.paldotourism.entity.Status.*;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestDto {

//    private String depTerminalName;
//    private String arrTerminalName;
//
//    private String depDate;
//
//    private String depTime;
//
//    private String busGrade;

    private List<Integer> seatNumbers;

    @Builder
    public ReservationRequestDto(List<Integer> seatNumbers)
    {
//        this.depTerminalName = depTerminalName;
//        this.arrTerminalName = arrTerminalName;
//        this.depDate = depDate;
//        this.depTime = depTime;
//        this.busGrade = busGrade;
        this.seatNumbers = seatNumbers;
    }

    public Reservation toEntity(Bus bus, User user, String reservationNumber)
    {
        return Reservation.builder()
                .user(user)
                .bus(bus)
                .reservationNumber(reservationNumber)
                .reservationStatus(STATUS_RESERVATIONING)
                .build();

    }


}
