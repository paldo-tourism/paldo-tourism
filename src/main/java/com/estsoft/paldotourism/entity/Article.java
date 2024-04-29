package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user; // 작성자 정보(FK)

    @Enumerated(EnumType.STRING)
    private Category category; // 카테고리

    @Column
    private String title; // 제목

    @Column(length = 10000)
    private String content; // 내용

    @Builder
    public Article(User user, Category category, String title, String content) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.content = content;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }
}
