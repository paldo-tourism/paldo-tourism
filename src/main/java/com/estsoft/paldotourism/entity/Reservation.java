package com.estsoft.paldotourism.entity;

import com.estsoft.paldotourism.dto.user.MyPageReservationDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user; // 유저 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Bus bus; // 버스 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private PaymentHistory paymentHistory; // 결제 정보(FK)

    @Column
    private String reservationNumber; // 예약 번호

    @Enumerated(EnumType.STRING)
    @Column
    private Status reservationStatus;


    @Builder
    public Reservation(User user, Bus bus, PaymentHistory paymentHistory, String reservationNumber, Status reservationStatus) {
        this.user = user;
        this.bus = bus;
        this.paymentHistory = paymentHistory;
        this.reservationNumber = reservationNumber;
        this.reservationStatus = reservationStatus;
    }

    public void updateReservationStatus(Status reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void updatePaymentHistory(PaymentHistory paymentHistory)
    {
        this.paymentHistory = paymentHistory;
    }


    public MyPageReservationDto toMyPageReservationDto(Boolean check)
    {
        return MyPageReservationDto.builder()
                .id(id)
                .user(user)
                .bus(bus)
                .paymentHistory(paymentHistory)
                .reservationNumber(reservationNumber)
                .reservationStatus(reservationStatus)
                .reservationTimeCheck(check)
                .build();

    }

}
