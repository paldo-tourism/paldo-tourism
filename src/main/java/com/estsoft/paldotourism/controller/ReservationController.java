package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

//    @GetMapping("/api/reservation/")
//    public ResponseEntity<List<Bus>> getAllBus(@RequestParam("depTerminal") String depTerminal, @RequestParam("arrTerminal") String arrTerminal, @RequestParam("depTime") String depTime, @RequestParam("busGrade") String busGrade)
//    {
//        return ResponseEntity.ok().body(reservationService.getAllBus(depTerminal,arrTerminal,depTime,busGrade));
//    }

    @PostMapping("/api/main/")
    public ResponseEntity<List<Bus>> postAllBus(@RequestParam("depTerminal") String depTerminal, @RequestParam("arrTerminal") String arrTerminal, @RequestParam("depTime") String depTime, @RequestParam("busGrade") String busGrade)
    {
        return ResponseEntity.ok().body(reservationService.getAllBus(depTerminal,arrTerminal,depTime,busGrade));
    }

//    @GetMapping("/api/reservation")
//    public ResponseEntity<List<Bus>> getAllBus()
//    {
//
//    }

}
