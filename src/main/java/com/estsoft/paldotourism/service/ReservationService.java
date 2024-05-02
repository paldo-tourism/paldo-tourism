package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.reservation.ReservationRequestDto;
import com.estsoft.paldotourism.entity.*;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.ReservationRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private BusRepository busRepository;
    private SeatRepository seatRepository;
    private UserRepository userRepository;


    public ReservationService(ReservationRepository reservationRepository, BusRepository busRepository, SeatRepository seatRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.busRepository = busRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }

    public void addReservation(Long busId, ReservationRequestDto requestDto, String userName) {

        Bus bus = busRepository.findById(busId).get();
        User user = userRepository.findByEmail(userName).get();
        String testReservationCode = "test";

        if(requestDto.getSeatNumbers().size() > 1)
        {
            for (int i = 0; i < requestDto.getSeatNumbers().size(); i++)
            {
                Reservation reservation = requestDto.toEntity(bus,user,testReservationCode);
                reservationRepository.save(reservation);

                Integer seat =  requestDto.getSeatNumbers().get(i);

                updateSeatReservationStatus(bus,seat,SeatStatus.SELECTED);

            }
        }
        else
        {
            Reservation reservation = requestDto.toEntity(bus,user,testReservationCode);
            reservationRepository.save(reservation);

            Integer seat =  requestDto.getSeatNumbers().get(0);

            updateSeatReservationStatus(bus,seat,SeatStatus.SELECTED);
        }
    }

    @Transactional
    public void updateSeatReservationStatus(Bus bus, Integer seatNumber, SeatStatus seatStatus)
    {
        Seat seat = seatRepository.findByBusAndSeatNumber(bus,seatNumber);
        seat.setStatus(seatStatus);
    }










    //출발시간 대신 출발날짜로 조회해야해서 DepTime -> DepDate로 변경했습니다
//    public List<Bus> getAllBus(String depTerminal, String arrTerminal, String depTime, String busGrade) {
//
//        return busRepository.findAllByDepTerminalAndArrTerminalAndDepTimeAndBusGrade(depTerminal,arrTerminal,depTime,busGrade);
//    }
}
