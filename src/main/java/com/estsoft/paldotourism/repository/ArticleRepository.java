package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Article;

import java.util.List;
import java.util.Optional;

import com.estsoft.paldotourism.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
  @EntityGraph(attributePaths = {"user"})
  Optional<Article> findById(Long id);

  List<Article> findByCategory(Category category);
}
