package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.Status;
import com.estsoft.paldotourism.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    @Query("SELECT b.depTerminal, b.arrTerminal, b.depTime, b.arrTime, b.charge, b.busGrade FROM Reservation r join Bus b on r.bus.id = b.id WHERE r.reservationNumber = :reservationNumber")
    List<String> findBusDetails(String reservationNumber);

    @Query("SELECT s.seatNumber FROM Reservation r join Seat s on r.id = s.reservation.id")
    List<String> findSeatDetails();

    Optional<Reservation> findByReservationNumber(String reservationNumber);

    List<Reservation> findAllByUser(User user);


    List<Reservation> findAllByUserAndReservationStatusIn(User user, List<Status> statusList);


}
