package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentResponseDTO;
import com.estsoft.paldotourism.entity.Article;
import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.entity.QArticle;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ArticleRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import static com.estsoft.paldotourism.entity.QArticle.article;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;

  private final CommentService commentService;

  private final UserRepository userRepository;

  private final JPAQueryFactory queryFactory;

  public List<ArticleResponseDTO> noticeList(){
    List<Article> noticeList = articleRepository.findByCategory(Category.CATEGORY_ANNOUNCEMENT);

      return noticeList.stream().map(this::toDTO).toList();
  }

  public PageResponseDTO<ArticleResponseDTO> articleFilteredList(String searchType, String keyword, Category category, Pageable pageable){

    BooleanBuilder filterBuilder = ArticleQuerydsl.createFilterBuilder(searchType, category, keyword, article);

    List<OrderSpecifier> orderSpecifier = ArticleQuerydsl.getOrderSpecifier(pageable.getSort(), article);

    List<Article> result = getFilteredResult(orderSpecifier, filterBuilder, pageable);

    long totalCount = queryFactory.selectFrom(article).where(filterBuilder).fetchCount();

    Page<ArticleResponseDTO> resultDTO = new PageImpl<>(result.stream().map(this::toDTO).toList(),pageable,totalCount);

    return new PageResponseDTO<>(resultDTO);
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
            .author(article.getUser().getNickName())
            .createdAt(article.getCreatedDateTime())
            .updatedAt(article.getModifiedDateTime())
            .isSecret(article.getIsSecret())
            .commentCount(article.getCommentCount())
            .state(article.getState())
            .build();
  }

  private List<Article> getFilteredResult(List<OrderSpecifier> orderSpecifier, BooleanBuilder filterBuilder, Pageable pageable){

    return queryFactory.selectFrom(article)
            .where(filterBuilder)
            .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
  }
}
