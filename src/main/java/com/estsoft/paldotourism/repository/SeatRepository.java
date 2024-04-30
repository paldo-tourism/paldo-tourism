package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {

    Integer countByBusAndStatus(Bus bus, SeatStatus status);
}
