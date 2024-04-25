package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;

public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 버스 아이디(PK)

    @Column
    private String depTerminal;

    @Column
    private String arrTerminal;

    @Column
    private String depTime;

    @Column
    private String arrTime;

    @Column
    private Integer totalSeatNumber;

    @Column
    private Integer charge;

    @Column
    private String busGrade;

}
