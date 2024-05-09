package com.estsoft.paldotourism.repository;

import com.estsoft.paldotourism.entity.Bus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {

    List<Bus> findAllByDepTerminalAndArrTerminalAndDepDateAndBusGrade(String depTerminal, String arrTerminal, String depDate, String busGrade);
}
