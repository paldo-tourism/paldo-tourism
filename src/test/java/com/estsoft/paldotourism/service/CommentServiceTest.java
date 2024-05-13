package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.qna.comment.CommentRequestDTO;
import com.estsoft.paldotourism.entity.Article;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ArticleRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArticleRepository articleRepository;

    User user;
    Article article;

    @BeforeEach
    void setTest(){
        user = userRepository.findByEmail("aaa@aaa.com").orElseThrow();
        article = articleRepository.findById(80L).orElseThrow();
    }

    @Test
    void writeParentComment(){
        CommentRequestDTO dto = CommentRequestDTO.builder()
                .articleId(80L)
                .author("aaa@aaa.com")
                .parentId(null)
                .content("댓글 작성 테스트")
                .build();

//        Comment comment = Comment.builder()
//                .article(article)
//                .user(user)
//                .content("댓글 작성 테스트")
//                .parent(null)
//                .build();

        commentService.writeComment(dto, user);
    }

    @Test
    void writeChildComment(){
        CommentRequestDTO dto = CommentRequestDTO.builder()
                .articleId(80L)
                .content("대댓글 작성 테스트")
                .parentId(1L)
                .author("aaa@aaa.com")
                .build();

        commentService.writeComment(dto, user);
    }

    @Test
    void commentList(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("path").ascending().and(Sort.by("createdDateTime")));
        System.out.println(commentService.getCommentList(80L, pageable).getDtoList());
    }

    @Test
    @Transactional
    void getChildCommentList(){
        System.out.println(commentService.getComment(1L).getChildren().get(0).getContent());
    }
}
