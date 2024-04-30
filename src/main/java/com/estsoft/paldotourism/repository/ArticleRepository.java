package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Article;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  @EntityGraph(attributePaths = {"user"})
  Optional<Article> findById(Long id);
}
