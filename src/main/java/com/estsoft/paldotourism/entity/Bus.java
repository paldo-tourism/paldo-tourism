package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 버스 아이디(PK)

    @Column
    private String depTerminal; // 출발 터미널

    @Column
    private String arrTerminal; // 도착 터미널

    @Column
    private String depTime; // 출발 시간

    @Column
    private String arrTime; // 도착 시간

    @Column
    private Integer totalSeatNumber; // 총 좌석 수

    @Column
    private Integer charge; // 요금

    @Column
    private String busGrade; // 등급

}
