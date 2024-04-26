package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 좌석 아이디(PK)

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
    private Status status; // 예약 상태
}
