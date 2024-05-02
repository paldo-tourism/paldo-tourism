package com.estsoft.paldotourism.dto.qna.article;

import com.estsoft.paldotourism.entity.Category;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleResponseDTO {
  private Long id;

  private String writer;

  private String title;

  private String content;

  private Category category;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private boolean isSecret;

  @Builder
  public ArticleResponseDTO(Long id, String writer, String title, String content, Category category, LocalDateTime createdAt,
      LocalDateTime updatedAt, boolean isSecret) {
    this.id = id;
    this.writer = writer;
    this.title = title;
    this.content = content;
    this.category = category;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.isSecret = isSecret;
  }

  public void updateIsSecret(String loginUserNickName){
    if(this.isSecret){
      if(loginUserNickName.equals(this.writer)){
        this.isSecret = false;
      }
    }
  }
}
