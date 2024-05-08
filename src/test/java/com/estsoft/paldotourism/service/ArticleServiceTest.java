package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.UserRepository;

import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleServiceTest {

  @Autowired
  private ArticleService articleService;

  @Autowired
  private UserRepository userRepository;

  User user;

  @BeforeEach
  void setUser(){
    user = userRepository.findByEmail("aa@aaa.com").orElseThrow();
  }

  @Test
  public void writeTest(){
    IntStream.rangeClosed(51,80).forEach(i->{
      ArticleRequestDTO articleDTO = ArticleRequestDTO.builder()
          .authorEmail(user.getEmail())
          .title("title" + i)
          .content("content" + i)
          .category(Category.CATEGORY_QA)
          .build();

      articleService.articleWrite(articleDTO);
    });
  }

  @Test
  public void readTest(){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(55L);

    System.out.println(articleResponseDTO);
  }

  @Test
  public void updateTest(){
//    articleService.articleUpdate("수정 테스트 제목", "수정 테스트 내용", Category.CATEGORY_QA,  55L);
    System.out.println(articleService.articleRead(55L));
  }

  @Test
  public void deleteTest(){
    articleService.articleDelete(56L);
    try{
      articleService.articleRead(56L);
    }catch (Exception e){
      System.out.println(e.getMessage());
    }
  }
}