package com.estsoft.paldotourism.dto.qna.article;

import com.estsoft.paldotourism.dto.qna.comment.CommentResponseDTO;
import com.estsoft.paldotourism.entity.Category;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.estsoft.paldotourism.entity.Role;
import com.estsoft.paldotourism.entity.User;
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

  private String author;

  private String title;

  private String content;

  private Category category;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private boolean isSecret;

  private PageResponseDTO<CommentResponseDTO> commentList;

  private Integer commentCount;

  private Boolean statement;

  @Builder
  public ArticleResponseDTO(Long id, String author, String title, String content, Category category, LocalDateTime createdAt,
                            LocalDateTime updatedAt, boolean isSecret, Integer commentCount, Boolean statement) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.content = content;
    this.category = category;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.isSecret = isSecret;
    this.commentCount = commentCount;
    this.statement = statement;
  }

  public void updateIsSecret(String author, Role role){
    if(this.isSecret){
      if(role == Role.ROLE_ADMIN || author.equals(this.author)){
        this.isSecret = false;
      }
    }
  }

  public void updateCommentList(PageResponseDTO<CommentResponseDTO> commentList){
    this.commentList = commentList;
  }

  public void updateStatement(Boolean statement){
    this.statement = statement;
  }

  public String convertLocaldatetimeToTime() {
    final int SEC = 60;
    final int MIN = 60;
    final int HOUR = 24;
    final int DAY = 30;
    final int MONTH = 12;

    LocalDateTime now = LocalDateTime.now();

    long diffTime = createdAt.until(now, ChronoUnit.SECONDS); // now보다 이후면 +, 전이면 -

    if (diffTime < SEC){
      return diffTime + "초전";
    }
    diffTime = diffTime / SEC;
    if (diffTime < MIN) {
      return diffTime + "분 전";
    }
    diffTime = diffTime / MIN;
    if (diffTime < HOUR) {
      return diffTime + "시간 전";
    }
    diffTime = diffTime / HOUR;
    if (diffTime < DAY) {
      return diffTime + "일 전";
    }
    diffTime = diffTime / DAY;
    if (diffTime < MONTH) {
      return diffTime + "개월 전";
    }
    diffTime = diffTime / MONTH;
      return diffTime + "년 전";
  }
}
