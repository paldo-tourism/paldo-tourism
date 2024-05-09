package com.estsoft.paldotourism.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Bus extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 버스 아이디(PK)

    @Column
    private String depTerminal; // 출발 터미널

    @Column
    private String arrTerminal; // 도착 터미널

    @Column
    private String depDate; //출발날짜

    @Column
    private String depTime; // 출발 시간

    @Column
    private String arrTime; // 도착 시간

    @Column
    private Integer totalSeatNumber; // 총 좌석 수 28,45

    @Column
    private Integer charge; // 요금

    @Column
    private String busGrade; // 등급

    @Builder
    private Bus(String depTerminal,String arrTerminal,String depDate,String depTime,String arrTime,Integer totalSeatNumber,Integer charge,String busGrade) {
        this.depTerminal = depTerminal;
        this.arrTerminal = arrTerminal;
        this.depDate = depDate;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.totalSeatNumber = totalSeatNumber;
        this.charge = charge;
        this.busGrade = busGrade;
    }

}
