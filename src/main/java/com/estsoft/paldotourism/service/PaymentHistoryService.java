package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.entity.PaymentHistory;
import com.estsoft.paldotourism.entity.PaymentStatus;
import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.Status;
import com.estsoft.paldotourism.repository.PaymentHistoryRepository;
import com.estsoft.paldotourism.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;
    private final ReservationRepository reservationRepository;

    public PaymentHistoryService(PaymentHistoryRepository paymentHistoryRepository, ReservationRepository reservationRepository) {
        this.paymentHistoryRepository = paymentHistoryRepository;
        this.reservationRepository = reservationRepository;
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

        reservation.updateReservationStatus(Status.STATUS_CANCEL);

        PaymentHistory paymentHistory = reservation.getPaymentHistory();

        paymentHistory.update(PaymentStatus.PAYMENT_STATUS_CANCEL);

    }




}
