package com.estsoft.paldotourism.dto.qna.article;

import com.estsoft.paldotourism.entity.Article;
import com.estsoft.paldotourism.entity.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleRequestDTO {

  private String title;

  private String content;

  @Setter
  private String authorEmail;

  private String author;

  private Category category;

  private Boolean isSecret;

  @Builder
  public ArticleRequestDTO(String title, String content, String author, String authorEmail, Category category, Boolean isSecret){
    this.title = title;

    this.content = content;

    this.author = author;

    this.authorEmail = authorEmail;

    this.category = category;

    this.isSecret = isSecret;
  }

}
