package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
