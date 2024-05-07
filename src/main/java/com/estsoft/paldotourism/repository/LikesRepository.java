package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Likes;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes,Integer> {

    Optional<Likes> findByUserIdAndBusId(Long id, Long busId);

    boolean existsByUserIdAndBusId(Long id, Long busId);
}
