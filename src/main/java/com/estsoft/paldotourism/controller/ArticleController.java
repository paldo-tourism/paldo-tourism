package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.qna.article.ArticleRequestDTO;
import com.estsoft.paldotourism.dto.qna.article.ArticleResponseDTO;
import com.estsoft.paldotourism.dto.qna.article.PageResponseDTO;
import com.estsoft.paldotourism.entity.Category;
import com.estsoft.paldotourism.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ArticleController {

  @Autowired
  private ArticleService articleService;

  @GetMapping("/article")
  public String articleList(Model model, @PageableDefault(page = 0, size = 15, sort ="createdDateTime",
   direction = Direction.DESC) Pageable pageable){
    PageResponseDTO pageResponseDTO = articleService.articleList(pageable);

    model.addAttribute("list", pageResponseDTO);

    return "/article/list";
  }

  @GetMapping("/article/{articleId}")
  public String articleRead(@PathVariable Long articleId, Model model){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);
    model.addAttribute("article", articleResponseDTO);

    return "/article/read";
  }

  @GetMapping("/article/write")
  public String articleWritePage(Model model){
    model.addAttribute("email", "test@naver.com");

    return "/article/write";
  }

  @PostMapping("/article/write")
  public String articleWrite(ArticleRequestDTO articleRequestDTO){
    Long articleId = articleService.articleWrite(articleRequestDTO);

    return "redirect:/article/"+articleId;
  }

  @GetMapping("/article/update/{articleId}")
  public String articleUpdatePage(Model model, @PathVariable Long articleId){
    ArticleResponseDTO articleResponseDTO = articleService.articleRead(articleId);

    model.addAttribute("article",articleResponseDTO);
    model.addAttribute("email","test@naver.com");

    return "/article/update";
  }

  @PutMapping("/article/update/{articleId}")
  public String articleUpdate(String title, String content, Category category, @PathVariable Long articleId){
    articleService.articleUpdate(title, content, category, articleId);

    return "redirect:/article/"+articleId;
  }

  @DeleteMapping("/article/delete/{articleId}")
  public String articleDelete(@PathVariable Long articleId){
    articleService.articleDelete(articleId);

    return "redirect:/article";
  }

}
