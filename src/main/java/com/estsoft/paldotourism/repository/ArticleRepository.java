package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Article;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {
  @EntityGraph(attributePaths = {"user"})
  Optional<Article> findById(Long id);

  Page<Article> findAllByTitleContains(String keyword, Pageable pageable);

  Page<Article> findAllByContentContains(String keyword, Pageable pageable);

  @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
  Page<Article> findAllByTitleOrContentContains(String keyword, Pageable pageable);
}
