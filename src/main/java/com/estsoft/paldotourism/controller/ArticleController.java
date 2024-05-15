package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.entity.Role;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  //목록
  @GetMapping("/article")
  public String articleList(Model model, @PageableDefault(page = 0, size = 15, sort ="createdDateTime",
          direction = Direction.DESC) Pageable pageable, @Nullable String searchType, @Nullable String keyword, @Nullable Category category){
//    PageResponseDTO<ArticleResponseDTO> pageResponseDTO = articleService.articleList(searchType, keyword, category, pageable);
    PageResponseDTO<ArticleResponseDTO> pageResponseDTO = articleService.articleFilteredList(searchType, keyword, category, pageable);
    List<ArticleResponseDTO> noticeDTOList = articleService.noticeList();

    if(!getLoginUserName().equals("anonymousUser")) {
      pageResponseDTO.getDtoList().forEach(x->x.updateIsSecret(getLoginUser().getNickName(), getLoginUser().getRole()));
    }

    List<Category> categoryList = Arrays.asList(Category.values());

    model.addAttribute("list", pageResponseDTO);
    model.addAttribute("noticeList", noticeDTOList);
    model.addAttribute("categoryList", categoryList);
    model.addAttribute("searchType", searchType);
    model.addAttribute("keyword", keyword);
    model.addAttribute("category", category);

    return "article/list";
  }

  //읽기
  @GetMapping("/article/{articleId}")
  public String articleRead(@PathVariable Long articleId, Model model){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);

    if(articleResponseDTO.isSecret()){
      identityVerification(articleResponseDTO.getAuthor(), getLoginUser());
    }
    model.addAttribute("article", articleResponseDTO);
    model.addAttribute("commentList", articleResponseDTO.getCommentList());
//    model.addAttribute("commentList", commentService.getCommentList(articleId,
//            PageRequest.of(0,10, Sort.Direction.ASC, "path")));

    return "article/readNew";
  }

  // 쓰기
  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @GetMapping("/article/write")
  public String articleWritePage(Model model){
    List<Category> categories = Arrays.stream(Category.values())
            .filter(category -> category != Category.CATEGORY_ANNOUNCEMENT)
            .toList();

    model.addAttribute("category", categories);

    return "article/write";
  }

  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @PostMapping("/article/write")
  public String articleWrite(ArticleRequestDTO articleRequestDTO){

    articleRequestDTO.setAuthorEmail(getLoginUser().getEmail());

    Long articleId = articleService.articleWrite(articleRequestDTO);

    return "redirect:/article/"+articleId;
  }

  // 수정
  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @GetMapping("/article/update/{articleId}")
  public String articleUpdatePage(Model model, @PathVariable Long articleId){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);
    List<Category> categories = Arrays.stream(Category.values())
            .filter(category -> category != Category.CATEGORY_ANNOUNCEMENT)
            .toList();

    model.addAttribute("category", categories);
    model.addAttribute("article",articleResponseDTO);

    return "article/update";
  }

  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @PutMapping("/article/update/{articleId}")
  public String articleUpdate(ArticleRequestDTO articleRequestDTO, @PathVariable Long articleId){

    System.out.println(getLoginUserName());
    System.out.println(articleRequestDTO.getAuthor());
    if(getLoginUserName().equals(articleRequestDTO.getAuthor()) || getLoginUser().getRole().equals(Role.ROLE_ADMIN)) {
      articleService.articleUpdate(articleRequestDTO, articleId);
    }else{
      throw new AuthorizationServiceException("수정 권한이 없습니다.");
    }

    return "redirect:/article/"+articleId;
  }

  //삭제
  @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
  @DeleteMapping("/article/delete/{articleId}")
  public String articleDelete(ArticleRequestDTO articleRequestDTO, @PathVariable Long articleId){
    if(getLoginUserName().equals(articleRequestDTO.getAuthor()) || getLoginUser().getRole().equals(Role.ROLE_ADMIN)) {
      articleService.articleDelete(articleId);
    }else{
      throw new AuthorizationServiceException("삭제 권한이 없습니다.");
    }

    return "redirect:/article";
  }

  //유저 정보 가져오기
  private String getLoginUserName(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  private User getLoginUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return (User)authentication.getPrincipal();
  }

  //권한 확인
  private void identityVerification(String writer, User user){
    if(writer.equals( user.getNickName()) || user.getRole() == Role.ROLE_ADMIN){
      return;
    }
    throw new AuthorizationServiceException("권한이 없습니다");
  }

}
