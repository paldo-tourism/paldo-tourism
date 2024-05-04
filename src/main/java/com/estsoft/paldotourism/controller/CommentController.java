package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentRequestDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentResponseDTO;
import com.estsoft.paldotourism.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/")
  public String getCommentList(Long articleId, Model model,
      @PageableDefault(page = 0, size = 10, sort = "createdDateTime", direction = Direction.ASC) Pageable pageable){
    PageResponseDTO<CommentResponseDTO> commentList = commentService.getCommentList(articleId, pageable);

    model.addAttribute("commentList", commentList);

    return "/board/article :: #all-comment";
  }

  @PostMapping("/")
  public void writeComment(CommentRequestDTO commentRequestDTO){
    commentService.writeComment(commentRequestDTO);
  }

  @DeleteMapping("/")
  public void deleteComment(@RequestParam Long commentId){
    commentService.deleteComment(commentId);
  }

}
