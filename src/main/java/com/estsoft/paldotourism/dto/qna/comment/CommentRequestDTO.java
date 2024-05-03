package com.estsoft.paldotourism.dto.qna.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {

  private String content;

  private String authorEmail;

  private Long articleId;

  @Builder
  public CommentRequestDTO(String content, String authorEmail, Long articleId) {
    this.content = content;
    this.authorEmail = authorEmail;
    this.articleId = articleId;
  }

}
