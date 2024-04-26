package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus,Integer> {

    List<Bus> findAllByDepTerminalAndArrTerminalAndDepTimeAndBusGrade(String depTerminal, String arrTerminal, String depTime, String busGrade);
}
