package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.ArticleService;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Log4j2
@Controller
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  //목록
  @GetMapping("/article")
  public String articleList(Model model, @PageableDefault(page = 0, size = 15, sort ="createdDateTime",
   direction = Direction.DESC) Pageable pageable, @Nullable String searchType, @Nullable String keyword){
    PageResponseDTO<ArticleResponseDTO> pageResponseDTO = articleService.articleList(searchType, keyword, getLoginUserName(), pageable);

    model.addAttribute("list", pageResponseDTO);
    model.addAttribute("searchType", searchType);
    model.addAttribute("keyword", keyword);

    return "/article/list";
  }

  //읽기
  @GetMapping("/article/{articleId}")
  public String articleRead(@PathVariable Long articleId, Model model){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);

    if(articleResponseDTO.isSecret() && (!articleResponseDTO.getWriter().equals(getLoginUserName()))){
      throw new AuthorizationServiceException("접근 권한이 없습니다.");
    }
    model.addAttribute("article", articleResponseDTO);

    return "/article/readNew";
  }

  // 쓰기
  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @GetMapping("/article/write")
  public String articleWritePage(){

    return "/article/write";
  }

  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @PostMapping("/article/write")
  public String articleWrite(ArticleRequestDTO articleRequestDTO){
    log.info(getLoginUserEmail());

    articleRequestDTO.setAuthorEmail(getLoginUserEmail());

    Long articleId = articleService.articleWrite(articleRequestDTO);

    return "redirect:/article/"+articleId;
  }

  // 수정
  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @GetMapping("/article/update/{articleId}")
  public String articleUpdatePage(Model model, @PathVariable Long articleId){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);

    identityVerification(articleResponseDTO.getWriter());

    model.addAttribute("article",articleResponseDTO);
    return "/article/update";
  }

  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @PutMapping("/article/update/{articleId}")
  public String articleUpdate(ArticleRequestDTO articleRequestDTO, @PathVariable Long articleId){
    articleService.articleUpdate(articleRequestDTO, articleId);

    return "redirect:/article/"+articleId;
  }

  //삭제
  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @DeleteMapping("/article/delete/{articleId}")
  public String articleDelete(@PathVariable Long articleId){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);

    identityVerification(articleResponseDTO.getWriter());

    articleService.articleDelete(articleId);

    return "redirect:/article";
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

  //권한 확인
  private void identityVerification(String writer){
    if(!writer.equals( getLoginUserName())){
      throw new AuthorizationServiceException("권한이 없습니다");
    }
  }

}
