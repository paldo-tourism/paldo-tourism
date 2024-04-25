package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;

public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 게시글 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Reservation reservation; // 작성자 정보(FK)

    @Column
    private Integer totalCharge; // 카테고리
}
