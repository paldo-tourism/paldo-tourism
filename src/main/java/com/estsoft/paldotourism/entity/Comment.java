package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 댓글 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user; // 댓글 작성자 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Article article; // 게시글 정보(FK)

    @Column
    private String content; // 내용

}
