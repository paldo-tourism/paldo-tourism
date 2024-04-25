package com.estsoft.paldotourism.entity;


import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 회원 아이디(PK)

    @Column
    private String email; // 이메일(사실상의 아이디)

    @Column
    private String nickName; // 닉네임

    @Column
    private String password; // 비밀번호

    @Column
    private String phoneNumber; // 전화번호

}
