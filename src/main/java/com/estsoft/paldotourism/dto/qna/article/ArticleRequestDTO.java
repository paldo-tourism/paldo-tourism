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
  private String writerEmail;

  private Category category;

  @Builder
  public ArticleRequestDTO(String title, String content, String writerEmail, Category category){
    this.title = title;

    this.content = content;

    this.writerEmail = writerEmail;

    this.category = category;
  }

}
