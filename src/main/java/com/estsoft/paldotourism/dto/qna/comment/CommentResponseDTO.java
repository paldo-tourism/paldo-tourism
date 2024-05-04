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
  private Long commentId;

  private String content;

  private String writer;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Builder
  public CommentResponseDTO(Long commentId, String content, String writer, LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.commentId = commentId;
    this.content = content;
    this.writer = writer;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
