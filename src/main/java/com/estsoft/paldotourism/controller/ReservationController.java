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

    @PostMapping("/api/reservation/{busid}")
    public void addReservation(@RequestBody ReservationRequestDto requestDto, @PathVariable Long busId, Principal principal)
    {
        UserDetails currentUser = userDetailService.loadUserByUsername(principal.getName());

        String userName = currentUser.getUsername();

        reservationService.addReservation(busId, requestDto,userName);
    }




//    @GetMapping("/api/reservation/")
//    public ResponseEntity<List<Bus>> getAllBus(@RequestParam("depTerminal") String depTerminal, @RequestParam("arrTerminal") String arrTerminal, @RequestParam("depTime") String depTime, @RequestParam("busGrade") String busGrade)
//    {
//        return ResponseEntity.ok().body(reservationService.getAllBus(depTerminal,arrTerminal,depTime,busGrade));
//    }

//    @PostMapping("/api/main/")
//    public ResponseEntity<List<Bus>> postAllBus(@RequestParam("depTerminal") String depTerminal, @RequestParam("arrTerminal") String arrTerminal, @RequestParam("depTime") String depTime, @RequestParam("busGrade") String busGrade)
//    {
//        return ResponseEntity.ok().body(reservationService.getAllBus(depTerminal,arrTerminal,depTime,busGrade));
//    }

//    @GetMapping("/api/reservation")
//    public ResponseEntity<List<Bus>> getAllBus()
//    {
//
//    }

}
