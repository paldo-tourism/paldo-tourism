package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 결제 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Reservation reservation; // 예약 정보(FK)

    @Column
    private Integer totalCharge; // 총 결제 금액
}
