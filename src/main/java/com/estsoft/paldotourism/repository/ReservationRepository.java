package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT u.nickName, u.email FROM Reservation r join User u on r.id = u.id")
    Object[] findUserDetails();

    @Query("SELECT b.depTerminal, b.arrTerminal, b.depTime, b.arrTime, b.charge, b.busGrade FROM Reservation r join Bus b on r.bus.id = b.id")
    Object[] findBusDetails();

    @Query("SELECT s.seatNumber FROM Reservation r join Seat s on r.bus.id = s.id")
    String findSeatDetails();

    @Query("SELECT r.reservationNumber FROM Reservation r")
    String findReservationDetails();


    Optional<Reservation> findByReservationNumber(String reservationNumber);

    List<Reservation> findAllByUser(User user);


}
