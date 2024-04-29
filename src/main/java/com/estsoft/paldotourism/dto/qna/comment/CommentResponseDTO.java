package com.estsoft.paldotourism.dto.qna.comment;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDTO {

  private String content;

  private String writer;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAT;

  @Builder
  public CommentResponseDTO(String content, String writer, LocalDateTime createdAt,
      LocalDateTime updatedAT) {
    this.content = content;
    this.writer = writer;
    this.createdAt = createdAt;
    this.updatedAT = updatedAT;
  }
}
