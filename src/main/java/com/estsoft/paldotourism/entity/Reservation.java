package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 게시글 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user; // 작성자 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Bus bus; // 작성자 정보(FK)

    @Column
    private String reservationNumber; // 카테고리

}
