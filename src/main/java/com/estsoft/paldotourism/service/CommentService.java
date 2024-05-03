package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentRequestDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentResponseDTO;
import com.estsoft.paldotourism.entity.Article;
import com.estsoft.paldotourism.entity.Comment;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.repository.ArticleRepository;
import com.estsoft.paldotourism.repository.CommentRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ArticleRepository articleRepository;

  public PageResponseDTO<CommentResponseDTO> getCommentList(Long articleId, Pageable pageable){
    Page<Comment> commentPage = commentRepository.findAllByArticleId(articleId, pageable);
    return new PageResponseDTO<>(commentPage.map(this::toDTO));
  }

  public void writeComment(CommentRequestDTO commentRequestDTO){
    User user = userRepository.findByEmail(commentRequestDTO.getAuthorEmail()).orElseThrow();
    Article article = articleRepository.findById(commentRequestDTO.getArticleId()).orElseThrow();

    commentRepository.save(toEntity(commentRequestDTO, user, article));
  }

  @Transactional
  public void updateComment(CommentRequestDTO commentRequestDTO, Long commentId){
    Comment comment = commentRepository.findById(commentId).orElseThrow();
    comment.updateContent(commentRequestDTO.getContent());
  }

  public void deleteComment(Long commentId){
    commentRepository.deleteById(commentId);
  }

  public Comment toEntity(CommentRequestDTO commentRequestDTO, User user, Article article){
    return Comment.builder()
        .user(user)
        .article(article)
        .content(commentRequestDTO.getContent())
        .build();
  }

  public CommentResponseDTO toDTO(Comment comment){
    return CommentResponseDTO.builder()
        .commentId(comment.getId())
        .writer(comment.getUser().getNickName())
        .content(comment.getContent())
        .updatedAt(comment.getModifiedDateTime())
        .createdAt(comment.getCreatedDateTime())
        .build();
  }
}
