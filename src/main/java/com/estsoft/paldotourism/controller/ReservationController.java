package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.reservation.ReservationRequestDto;
import com.estsoft.paldotourism.entity.*;
import com.estsoft.paldotourism.exception.reservation.ReservationNotAllowedException;
import com.estsoft.paldotourism.service.BusApiService;
import com.estsoft.paldotourism.service.ReservationService;
import com.estsoft.paldotourism.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class ReservationController {

    private ReservationService reservationService;
    private BusApiService busApiService;
    private UserDetailService userDetailService;

    public ReservationController(ReservationService reservationService, BusApiService busApiService, UserDetailService userDetailService) {
        this.reservationService = reservationService;
        this.busApiService = busApiService;
        this.userDetailService = userDetailService;
    }

    // 예약 추가(예약 데이터를 테이블에 추가함)
    @PostMapping("/api/reservation/{busId}")
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequestDto requestDto, @PathVariable Long busId, Principal principal) {
        Optional<User> currentUser = userDetailService.getCurrentUser();
        String userName = currentUser.get().getEmail();

        if (requestDto.getSeatNumbers().size() < 1 || requestDto.getSeatNumbers().size() > 10) {
            throw new ReservationNotAllowedException();
        }

        Long reservationId = reservationService.addReservation(busId, requestDto, userName);
        return ResponseEntity.ok(reservationId);
    }

    // 예약 확인(예약 ID로 예약 데이터 1개 찾기)
    @GetMapping("/api/reservation/{reservationId}")
    public ResponseEntity<?> showReservation(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.showOneReservation(reservationId);

        return ResponseEntity.ok(reservation);
    }

    // 예약 확인(현재 유저로 예약 리스트 찾기)
    @GetMapping("/api/reservations/currentUser")
    public ResponseEntity<List<?>> showAllReservationByUser() {
        Optional<User> currentUser = userDetailService.getCurrentUser();
        String userName = currentUser.get().getEmail();

        List<Reservation> reservationList = reservationService.showAllReservation(userName);

        return ResponseEntity.ok(reservationList);
    }

    // 예약 확인(현재 유저와 예약 상태(예약 완료, 예약 취소)로 예약 리스트 찾기)
    @GetMapping("/api/reservations/currentUser/status")
    public ResponseEntity<List<?>> showAllReservationByUserAndStatus() {
        Optional<User> currentUser = userDetailService.getCurrentUser();
        String userName = currentUser.get().getEmail();

        List<Reservation> reservationList = reservationService.showAllReservationByStatus(userName);
        return ResponseEntity.ok(reservationList);
    }


    // 예약 변경(기존 예약 데이터의 예약 상태를 취소로 바꾸고 새로운 예약 데이터를 테이블에 추가함)
    @PutMapping("/api/reservation/change/{reservationId}/{busId}")
    public void changeReservation(@RequestBody ReservationRequestDto requestDto, @PathVariable Long reservationId, @PathVariable Long busId, Principal principal) {
        UserDetails currentUser = userDetailService.loadUserByUsername(principal.getName());
        String userName = currentUser.getUsername();

        reservationService.changeReservation(reservationId, busId, requestDto, userName);
    }

    // 예약 취소(기존 예약 데이터의 예약 상태를 취소로 변경)
    @PutMapping("/api/reservation/cancel/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId) {
        //코드 개선을 위해 반환 타입을 void에서 ResponseEntity로 변경
//        reservationService.cancelReservation(reservationId);

        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok().body("{\"success\": true}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }


    // 예약 ID로 해당 예약으로 예약한 좌석 리스트 반환
    @GetMapping("/api/seat/reservation/{reservationId}")
    public ResponseEntity<List<?>> showAllSeatByReservation(@PathVariable Long reservationId) {
        List<Seat> seatList = reservationService.showAllSeatByReservation(reservationId);

        return ResponseEntity.ok(seatList);
    }

    // 버스 ID로 버스에 해당하는 좌석 리스트 반환
    @GetMapping("/api/seat/bus/{busId}")
    public ResponseEntity<List<?>> showAllSeatByBus(@PathVariable Long busId) {
        List<Seat> seatList = reservationService.showAllSeatByBus(busId);

        return ResponseEntity.ok(seatList);
    }

    //좌석 상태 업데이트
    @PutMapping("/api/seat/update/{busId}/{seatNumber}")
    public ResponseEntity<?> updateSeatReservationStatus(@PathVariable Long busId, @PathVariable Integer seatNumber, @RequestParam SeatStatus status) {
        try {
            Bus bus = busApiService.getBusById(busId);
            reservationService.updateSeatReservationStatus(bus, seatNumber, status);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }


    //좌석 예약 Id 업데이트
    @PutMapping("/api/seat/update/{reservationId}/{busId}/{seatNumber}")
    public ResponseEntity<?> updateSeatReservation(@PathVariable Long busId, @PathVariable Integer seatNumber, @PathVariable Long reservationId) {
        try {
            Bus bus = busApiService.getBusById(busId);
            Reservation reservation = reservationService.showOneReservation(reservationId);
            reservationService.updateSeatReservation(reservation, bus, seatNumber);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail");
        }
    }


    // 좌석 중복 체크
    @GetMapping("/api/seat/checkDuplicate/{busId}/{seatNumber}")
    public ResponseEntity<?> updateSeatReservation(@PathVariable Long busId, @PathVariable Integer seatNumber) {

        Bus bus = busApiService.getBusById(busId);
        Boolean check = reservationService.checkDuplication(bus, seatNumber);
        return ResponseEntity.ok().body(check);
    }

    
    // 예약 코드 생성
    @GetMapping("/api/generateCode")
    public ResponseEntity<?> generateReservationCode() {
        
        String code = reservationService.generateReservationCode();
        return ResponseEntity.ok().body(code);
    }

    // 해당 예약의 총 금액 반환
    @GetMapping("/api/totalCharge/{reservationId}")
    public ResponseEntity<?> getTotalCharge(@PathVariable Long reservationId) {

        Long totalCharge = reservationService.getTotalCharge(reservationId);
        return ResponseEntity.ok().body(totalCharge);
    }

    // 해당 예약의 인원 수 반환
    @GetMapping("/api/totalSeat/{reservationId}")
    public ResponseEntity<?> getTotalSeat(@PathVariable Long reservationId) {

        Long totalSeat = reservationService.getTotalSeat(reservationId);
        return ResponseEntity.ok().body(totalSeat);
    }


}
