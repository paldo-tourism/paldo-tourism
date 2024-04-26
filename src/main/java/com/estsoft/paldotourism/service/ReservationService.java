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

    public List<Bus> getAllBus(String depTerminal, String arrTerminal, String depTime, String busGrade) {

        return busRepository.findAllByDepTerminalAndArrTerminalAndDepTimeAndBusGrade(depTerminal,arrTerminal,depTime,busGrade);
    }
}
