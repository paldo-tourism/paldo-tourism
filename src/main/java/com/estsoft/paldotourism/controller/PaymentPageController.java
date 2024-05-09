package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.entity.PaymentHistory;
import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.service.PaymentHistoryService;
import com.estsoft.paldotourism.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
public class PaymentPageController {

    private PaymentHistoryService paymentHistoryService;
    private ReservationService reservationService;

    public PaymentPageController(PaymentHistoryService paymentHistoryService, ReservationService reservationService) {
        this.paymentHistoryService = paymentHistoryService;
        this.reservationService = reservationService;
    }

    // 결제 페이지 뷰
    @GetMapping("/{reservationId}/payment")
    public String getPayment(Model model, @PathVariable Long reservationId)
    {
        // 페이지에 전달해줄 정보 가져오고 전달
        Reservation reservation = reservationService.showOneReservation(reservationId);
        List<Seat> seatList = reservationService.showAllSeatByReservation(reservationId);
        Long totalCharge = reservationService.getTotalCharge(reservationId);
        Long totalSeat = reservationService.getTotalSeat(reservationId);

        model.addAttribute("reservationInfo",reservation);
        model.addAttribute("seats",seatList);
        model.addAttribute("totalCharge",totalCharge);
        model.addAttribute("totalSeat",totalSeat);


        return "/payment/payment";
    }

    @GetMapping("/{reservationId}/paymentComplete")
    public String getPaymentComplete(Model model, @PathVariable Long reservationId)
    {

        // 페이지에 전달해줄 정보 가져오고 전달
        Reservation reservation = reservationService.showOneReservation(reservationId);
        List<Seat> seatList = reservationService.showAllSeatByReservation(reservationId);
        Long totalCharge = reservationService.getTotalCharge(reservationId);
        Long totalSeat = reservationService.getTotalSeat(reservationId);

        model.addAttribute("reservationInfo",reservation);
        model.addAttribute("seats",seatList);
        model.addAttribute("totalCharge",totalCharge);
        model.addAttribute("totalSeat",totalSeat);


        return "/payment/paymentComplete";
    }
}
