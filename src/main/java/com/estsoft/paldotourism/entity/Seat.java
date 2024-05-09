package com.estsoft.paldotourism.entity;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor
public class Seat extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좌석 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Bus bus; // 버스 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Reservation reservation; // 예약 정보(FK)

    @Column
    private Integer seatNumber; // 좌석 번호

    @Enumerated(EnumType.STRING)
    @Column
    private SeatStatus status; // 예약 상태

    @Builder
    private Seat(Bus bus, int seatNumber, SeatStatus status) {
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.status = status;
        //TODO reservation은 일단 null로 둘 것인지?...
        this.reservation = null;
    }

    public void updateStatus(SeatStatus status)
    {
        this.status = status;
    }

    public void updateReservation(Reservation reservation)
    {
        this.reservation = reservation;
    }
}
