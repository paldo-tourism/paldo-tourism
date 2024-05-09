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

  @Transactional
  public Comment getComment(Long commentId){
    return commentRepository.findById(commentId).orElseThrow();
  }

  public PageResponseDTO<CommentResponseDTO> getCommentList(Long articleId, Pageable pageable){
    Page<Comment> commentPage = commentRepository.findAllByArticleId(articleId, pageable);
    return new PageResponseDTO<>(commentPage.map(this::toDTO));
  }

  public void writeComment(CommentRequestDTO commentRequestDTO){
    User user = userRepository.findByEmail(commentRequestDTO.getAuthor()).orElseThrow();
    Article article = articleRepository.findById(commentRequestDTO.getArticleId()).orElseThrow();
    Comment parent;
    if(commentRequestDTO.getParentId() != null){
      parent = commentRepository.findById(commentRequestDTO.getParentId()).orElseThrow();
    }else{
      parent = null;
    }

    commentRepository.save(toEntity(commentRequestDTO, user, article, parent));
  }

  @Transactional
  public void updateComment(CommentRequestDTO commentRequestDTO, Long commentId){
    Comment comment = commentRepository.findById(commentId).orElseThrow();
    comment.updateContent(commentRequestDTO.getContent());
  }

  @Transactional
  public void deleteComment(Long articleId, Long commentId){
    Comment comment = commentRepository.findById(commentId).orElseThrow();
    if(!comment.getChildren().isEmpty()){
      comment.updateIsDeleted(true);
    }else{
      commentRepository.delete(getDeletableAncestorComment(comment));
    }
  }

  private Comment getDeletableAncestorComment(Comment comment) { // 삭제 가능한 조상 댓글을 구함
    Comment parent = comment.getParent(); // 현재 댓글의 부모를 구함
    if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted() == true)
      return getDeletableAncestorComment(parent);
    return comment;
  }

  public Comment toEntity(CommentRequestDTO commentRequestDTO, User user, Article article, Comment parent){
    return Comment.builder()
            .user(user)
            .article(article)
            .content(commentRequestDTO.getContent())
            .parent(parent)
            .build();
  }

  public CommentResponseDTO toDTO(Comment comment){
    if(comment.getIsDeleted()){
      return CommentResponseDTO.builder()
              .content("삭제된 댓글입니다.")
              .depth(comment.getDepth())
              .build();
    }

    return CommentResponseDTO.builder()
            .commentId(comment.getId())
            .author(comment.getUser().getNickName())
            .content(comment.getContent())
            .depth(comment.getDepth())
            .updatedAt(comment.getModifiedDateTime())
            .createdAt(comment.getCreatedDateTime())
            .build();
  }
}
