package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentResponseDTO;
import com.estsoft.paldotourism.entity.Article;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ArticleRepository;
import com.estsoft.paldotourism.repository.CommentRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private CommentService commentService;

  @Autowired
  private UserRepository userRepository;

  public PageResponseDTO<ArticleResponseDTO> articleList(String searchType, String keyword, String loginUserNickname, Pageable pageable){

    PageResponseDTO<ArticleResponseDTO> responseDTO;

    if(searchType == null || searchType.isEmpty()){
      responseDTO = new PageResponseDTO<>(articleRepository.findAll(pageable).map(this::toDTO));
      responseDTO.getDtoList().forEach(x->x.updateIsSecret(loginUserNickname));
      return responseDTO;
    }

    if(searchType.equals("t")){
      responseDTO = new PageResponseDTO<>(articleRepository.findAllByTitleContains(keyword, pageable).map(this::toDTO));
    }else if(searchType.equals("c")){
      responseDTO = new PageResponseDTO<>(articleRepository.findAllByContentContains(keyword, pageable).map(this::toDTO));
    }else {
      responseDTO = new PageResponseDTO<>(articleRepository.findAllByTitleOrContentContains(keyword, pageable).map(this::toDTO));
    }

    responseDTO.getDtoList().forEach(x->x.updateIsSecret(loginUserNickname));

    return responseDTO;
  }

  public ArticleResponseDTO articleRead(Long articleId){
    Article article = articleRepository.findById(articleId).orElseThrow(()-> new NoSuchElementException("없는 글입니다."));
    ArticleResponseDTO articleResponseDTO = toDTO(article);

    PageResponseDTO<CommentResponseDTO> commentList = commentService.getCommentList(articleId,
            PageRequest.of(0,10, Sort.Direction.ASC, "path"));
    articleResponseDTO.updateCommentList(commentList);

    return articleResponseDTO;
  }

  public Long articleWrite(ArticleRequestDTO articleRequestDTO){
    return articleRepository.save(toEntity(articleRequestDTO)).getId();
  }

  @Transactional
  public void articleUpdate(ArticleRequestDTO articleRequestDTO, Long articleId){
    Article article = articleRepository.findById(articleId).orElseThrow(()-> new NoSuchElementException("없는 글입니다."));
    article.updateCategory(articleRequestDTO.getCategory());
    article.updateTitle(articleRequestDTO.getTitle());
    article.updateContent(articleRequestDTO.getContent());
    article.updateIsSecret(articleRequestDTO.getIsSecret());
  }

  public void articleDelete(Long articleId){
    articleRepository.deleteById(articleId);
  }

  private Article toEntity(ArticleRequestDTO dto){
    User user = userRepository.findByEmail(dto.getAuthorEmail()).orElseThrow();

    return Article.builder()
            .user(user)
            .title(dto.getTitle())
            .content(dto.getContent())
            .category(dto.getCategory())
            .isSecret(dto.getIsSecret())
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
            .isSecret(article.getIsSecret())
            .commentCount(article.getCommentCount())
            .build();
  }

  //유저 정보 가져오기
  private User getAuthenticatedUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }
}
