package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Integer> {
}
