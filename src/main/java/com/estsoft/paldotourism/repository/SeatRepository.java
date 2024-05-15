package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {

    Integer countByBusAndStatus(Bus bus, SeatStatus status);

    Seat findByBusAndSeatNumber(Bus bus, Integer seatNumber);

    List<Seat> findAllByBus(Bus bus);

    List<Seat> findAllByReservation(Reservation reservation);

    Long countByReservation(Reservation reservation);

    List<Seat> findByBusId(Long busId);

    @Query("SELECT s FROM Seat s JOIN s.reservation r WHERE r.reservationNumber = :reservationNumber")
    List<Seat> findByReservationNumber(@Param("reservationNumber") String reservationNumber);

    List<Seat> findAllByStatus(SeatStatus seatStatus);

    @Modifying
    @Query("UPDATE Seat s SET s.reservation = NULL, s.status = 'EMPTY' WHERE s.reservation.id IN (SELECT r.id FROM Reservation r WHERE r.user.id = :userId)")
    void updateSeatsByUserId(@Param("userId") Long userId);
}
