package com.estsoft.paldotourism.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 아이디(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user; // 댓글 작성자 정보(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Article article; // 게시글 정보(FK)

    @Column
    private String content; // 내용

    @Column
    private Boolean isDeleted;

    @Column
    private String path;

    @Column
    private Integer depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;


    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children;

    @Builder
    public Comment(User user, Article article, Comment parent, String content) {
        this.user = user;
        this.article = article;
        this.parent = parent;
        this.content = content;
        this.depth = 0;
        this.isDeleted = false;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void updateIsDeleted(Boolean isDeleted){ this.isDeleted = isDeleted; }

    @PostPersist
    public void postPersist() {
        if (parent == null) {
            this.path = String.valueOf(this.id);
        } else {
            this.path = parent.getPath() + "-" + id;
            this.depth = parent.getDepth() + 1;
        }
    }
}
