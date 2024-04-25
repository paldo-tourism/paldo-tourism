package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

}
