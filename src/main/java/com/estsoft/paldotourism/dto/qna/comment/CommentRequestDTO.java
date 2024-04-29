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

  private String writer;

  private Long articleId;

  @Builder
  public CommentRequestDTO(String content, String writer, Long articleId) {
    this.content = content;
    this.writer = writer;
    this.articleId = articleId;
  }


}
