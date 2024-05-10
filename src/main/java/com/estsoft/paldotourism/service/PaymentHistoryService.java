package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.entity.*;
import com.estsoft.paldotourism.repository.PaymentHistoryRepository;
import com.estsoft.paldotourism.repository.ReservationRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final ReservationRepository reservationRepository;


    private final SeatRepository seatRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository, ReservationRepository reservationRepository, SeatRepository seatRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.reservationRepository = reservationRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    // 결제 기록 저장
    public void createPaymentHistory(Long reservationId) {

        // 결제 데이터를 결제 기록 테이블에 저장
        PaymentHistory paymentHistory = PaymentHistory.builder().paymentStatus(PaymentStatus.PAYMENT_STATUS_SUCCESS).build();
        paymentHistoryRepository.save(paymentHistory);

        // 예약 테이블의 예약 데이터에 결제 외래키 연결 및 예약 상태 변경
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.updatePaymentHistory(paymentHistory);
        reservation.updateReservationStatus(Status.STATUS_RESERVATION);

        // 좌석 상태를 선택 중에서 예약 완료로 변경
        Bus reservationedBus = reservation.getBus();
        List<Seat> seatList = seatRepository.findAllByReservation(reservation);

        for (Seat seat : seatList) {
            Integer seatNumber = seat.getSeatNumber();

            // ReservationService에 있는 updateSeatReservationStatus 함수를 의존성 문제로 내부 코드만 가져옴
            Seat resultSeat = seatRepository.findByBusAndSeatNumber(reservationedBus, seatNumber);
            resultSeat.updateStatus(SeatStatus.OCCUPIED);
        }

    }

    // 결제 id로 결제 기록 1개 조회
    public PaymentHistory getPaymentHistoryById(Integer id) {

        return paymentHistoryRepository.findById(id).get();
    }

    // 예약 id로 결제 기록 1개 조회
    public PaymentHistory getPaymentHistoryByReservation(Long reservationId)
    {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        return reservation.getPaymentHistory();
    }

    // 결제 기록 변경(구현 하긴했으나 아래의 결제 취소 함수만 사용하면 될 것 같음)
    @Transactional
    public PaymentHistory updatePaymentHistory (Long reservationId, PaymentStatus status) {

        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.updateReservationStatus(Status.STATUS_CANCEL);

        PaymentHistory paymentHistory = reservation.getPaymentHistory();
        paymentHistory.update(status);

        return paymentHistory;
    }


    // 결제 취소(결제 데이터를 지우지 않고 결제 상태만 취소로 변경)
    @Transactional
    public void cancelPaymentHistory(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId).get();

        //reservation.updateReservationStatus(Status.STATUS_CANCEL);

        PaymentHistory paymentHistory = reservation.getPaymentHistory();

        paymentHistory.update(PaymentStatus.PAYMENT_STATUS_CANCEL);

    }




}
