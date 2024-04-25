package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer>
{
}
