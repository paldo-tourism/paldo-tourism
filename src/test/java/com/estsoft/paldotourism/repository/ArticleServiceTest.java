package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.ArticleService;
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

//  @BeforeEach
//  void setUser(){
//    user = new User("test@naver.com", "test", "1234", "010-1234-5678", Role.ROLE_USER);
//    userRepository.save(user);
//  }

  @Test
  public void writeTest(){
    IntStream.rangeClosed(1,100).forEach(i->{
      ArticleRequestDTO articleDTO = ArticleRequestDTO.builder()
          .writerEmail(user.getEmail())
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
    articleService.articleUpdate("수정 테스트 제목", "수정 테스트 내용", Category.CATEGORY_QA,  55L);
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