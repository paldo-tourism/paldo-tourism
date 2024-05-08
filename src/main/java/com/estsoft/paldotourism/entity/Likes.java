package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Likes extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 찜 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user; // 유저 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Bus bus; // 버스 정보(FK)

    @Builder
    private Likes(User user,Bus bus) {
        this.user = user;
        this.bus = bus;
    }
}
