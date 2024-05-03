package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.reservation.ReservationRequestDto;
import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.service.ReservationService;
import com.estsoft.paldotourism.service.UserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;
    private UserDetailService userDetailService;

    public ReservationController(ReservationService reservationService, UserDetailService userDetailService) {
        this.reservationService = reservationService;
        this.userDetailService = userDetailService;
    }

    // 예약 추가(예약 데이터를 테이블에 추가함)
    @PostMapping("/api/reservation/{busId}")
    public void addReservation(@RequestBody ReservationRequestDto requestDto, @PathVariable Long busId, Principal principal)
    {
        UserDetails currentUser = userDetailService.loadUserByUsername(principal.getName());
        String userName = currentUser.getUsername();

        reservationService.addReservation(busId, requestDto,userName);
    }

    // 예약 변경(기존 예약 데이터의 예약 상태를 취소로 바꾸고 새로운 예약 데이터를 테이블에 추가함)
    @PutMapping("/api/reservation/change/{reservationId}/{busId}")
    public void changeReservation(@RequestBody ReservationRequestDto requestDto, @PathVariable Long reservationId, @PathVariable Long busId, Principal principal)
    {
        UserDetails currentUser = userDetailService.loadUserByUsername(principal.getName());
        String userName = currentUser.getUsername();

        reservationService.changeReservation(reservationId,busId, requestDto,userName);
    }

    // 예약 취소(기존 예약 데이터의 예약 상태를 취소로 변경)
    @PutMapping("/api/reservation/cancel/{reservationId}")
    public void cancelReservation(@PathVariable Long reservationId)
    {

        reservationService.cancelReservation(reservationId);
    }


}
