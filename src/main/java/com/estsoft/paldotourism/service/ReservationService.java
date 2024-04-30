package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private BusRepository busRepository;

    public ReservationService(ReservationRepository reservationRepository, BusRepository busRepository) {
        this.reservationRepository = reservationRepository;
        this.busRepository = busRepository;
    }

    //출발시간 대신 출발날짜로 조회해야해서 DepTime -> DepDate로 변경했습니다
//    public List<Bus> getAllBus(String depTerminal, String arrTerminal, String depTime, String busGrade) {
//
//        return busRepository.findAllByDepTerminalAndArrTerminalAndDepTimeAndBusGrade(depTerminal,arrTerminal,depTime,busGrade);
//    }
}
