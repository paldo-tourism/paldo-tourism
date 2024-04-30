package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.entity.Article;
import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ArticleRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private UserRepository userRepository;

  public PageResponseDTO articleList(String searchType, String keyword, Pageable pageable){
    if(searchType == null || searchType.isEmpty()){
      return new PageResponseDTO(articleRepository.findAll(pageable).map(this::toDTO));
    }

    PageResponseDTO responseDTO;

    if(searchType.equals("t")){
      responseDTO = new PageResponseDTO(articleRepository.findAllByTitleContains(keyword, pageable).map(this::toDTO));
    }else if(searchType.equals("c")){
      responseDTO = new PageResponseDTO(articleRepository.findAllByContentContains(keyword, pageable).map(this::toDTO));
    }else {
      responseDTO = new PageResponseDTO(articleRepository.findAllByTitleOrContentContains(keyword, pageable).map(this::toDTO));
    }

    return responseDTO;
  }

  public ArticleResponseDTO articleRead(Long articleId){
    return toDTO(articleRepository.findById(articleId).orElseThrow(()-> new NoSuchElementException("없는 글입니다.")));
  }

  public Long articleWrite(ArticleRequestDTO articleRequestDTO){
    return articleRepository.save(toEntity(articleRequestDTO)).getId();
  }

  @Transactional
  public void articleUpdate(String title, String content, Category category, Long articleId){
    Article article = articleRepository.findById(articleId).orElseThrow(()-> new NoSuchElementException("없는 글입니다."));
    article.updateCategory(category);
    article.updateTitle(title);
    article.updateContent(content);
  }

  public void articleDelete(Long articleId){
    articleRepository.deleteById(articleId);
  }

  private Article toEntity(ArticleRequestDTO dto){
    User user = userRepository.findByEmail(dto.getWriterEmail()).orElseThrow();

    return Article.builder()
        .user(user)
        .title(dto.getTitle())
        .content(dto.getContent())
        .category(dto.getCategory())
        .build();
  }

  private ArticleResponseDTO toDTO(Article article){
    return ArticleResponseDTO.builder()
        .id(article.getId())
        .title(article.getTitle())
        .content(article.getContent())
        .category(article.getCategory())
        .writer(article.getUser().getNickName())
        .createdAt(article.getCreatedDateTime())
        .updatedAt(article.getModifiedDateTime())
        .build();
  }
}
