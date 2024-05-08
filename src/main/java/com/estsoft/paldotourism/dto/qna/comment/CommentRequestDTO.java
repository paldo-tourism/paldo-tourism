package com.estsoft.paldotourism.dto.qna.comment;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {

  private String content;

  @Setter
  private String author;

  private Long articleId;

  private Long parentId;

  @Builder
  public CommentRequestDTO(String content, String author, Long articleId, Long parentId) {
    this.content = content;
    this.author = author;
    this.articleId = articleId;
    this.parentId = parentId;
  }

}
