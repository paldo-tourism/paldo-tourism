package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.reservation.ReservationRequestDto;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.exception.reservation.ReservationNotAllowedException;
import com.estsoft.paldotourism.service.ReservationService;
import com.estsoft.paldotourism.service.UserDetailService;
import java.security.Principal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequestDto requestDto, @PathVariable Long busId, Principal principal)
    {
        Optional<User> currentUser = userDetailService.getCurrentUser();
        String userName = currentUser.get().getEmail();

        if(requestDto.getSeatNumbers().size() < 1 || requestDto.getSeatNumbers().size() > 10) {
            throw new ReservationNotAllowedException();
        }

        Long reservationId = reservationService.addReservation(busId, requestDto,userName);
        return ResponseEntity.ok(reservationId);
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
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId)
    {
        //코드 개선을 위해 반환 타입을 void에서 ResponseEntity로 변경
//        reservationService.cancelReservation(reservationId);

        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }


}
