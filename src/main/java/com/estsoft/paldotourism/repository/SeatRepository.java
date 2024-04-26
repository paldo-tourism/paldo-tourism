package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat,Integer> {
}
