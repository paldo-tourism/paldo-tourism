package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
  @EntityGraph(attributePaths = {"user"})
  Page<Comment> findAllByArticleId(Long articleId, Pageable pageable);

  @Query(value = "select Comment from Comment c where c.article.id = :articleId and c.parent.id = :commentId")
  boolean existsChild(Long articleId, Long commentId);

  boolean existsByParentId(Long parentId);

    void deleteByUserId(Long userId);
}
