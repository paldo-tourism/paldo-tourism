package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentRequestDTO;
import com.estsoft.paldotourism.dto.qna.comment.CommentResponseDTO;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

  @Autowired
  private CommentService commentService;


  @GetMapping("")
  public String getCommentList(Long articleId, Model model,
                               @PageableDefault(page = 0, size = 10, sort = "path", direction = Direction.ASC) Pageable pageable){
    PageResponseDTO<CommentResponseDTO> commentList = commentService.getCommentList(articleId, pageable);

    model.addAttribute("commentList", commentList);

    return "article/readNew :: #all-comment";
  }

  @ResponseBody
  @PreAuthorize(value = "isAuthenticated() or hasRole('ROLE_ADMIN')")
  @PostMapping("/write")
  public void writeComment(@RequestBody CommentRequestDTO commentRequestDTO){
    commentRequestDTO.setAuthor(getLoginUserEmail());

    commentService.writeComment(commentRequestDTO, getLoginUser());
  }

  @ResponseBody
  @PreAuthorize(value = "isAuthenticated() or hasRole('ROLE_ADMIN')")
  @PutMapping("/{commentId}")
  public void modifyComment(@RequestBody CommentRequestDTO commentRequestDTO, @PathVariable Long commentId){

    if(getLoginUserName().equals(commentRequestDTO.getAuthor())){
      commentService.updateComment(commentRequestDTO, commentId);
    }else{
      throw new AuthorizationServiceException("권한이 없습니다.");
    }
  }

  @ResponseBody
  @PreAuthorize(value = "isAuthenticated() or hasRole('ROLE_ADMIN')")
  @DeleteMapping("/{commentId}")
  public void deleteComment(String author, Long articleId, @PathVariable Long commentId){

    if(getLoginUserName().equals(author)){
      commentService.deleteComment(articleId, commentId);
    }else{
      throw new AuthorizationServiceException("권한이 없습니다.");
    }

  }

  //유저 정보 가져오기
  private String getLoginUserName(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  private String getLoginUserEmail(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User)authentication.getPrincipal();
    return user.getEmail();
  }

  private User getLoginUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (User)authentication.getPrincipal();
  }

  private void identityVerification(String writer){
    if(!writer.equals( getLoginUserName())){
      throw new AuthorizationServiceException("권한이 없습니다");
    }
  }
}
