package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.bus.SeatBusResponseDto;
import com.estsoft.paldotourism.dto.bus.SeatResponseDto;
import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.exception.bus.BusNotFoundException;
import com.estsoft.paldotourism.exception.reservation.ReservationNotAllowedException;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final SeatRepository seatRepository;
    private final BusRepository busRepository;

    @Transactional(readOnly = true)
    public List<SeatResponseDto> getSeatsByBusId(Long busId) {
        return seatRepository.findByBusId(busId).stream().map(SeatResponseDto::of).collect(
            Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SeatBusResponseDto getBusByBusId(Long busId) {
        Bus bus = busRepository.findById(busId).orElseThrow(BusNotFoundException::new);

        if(!canMakeReservation(bus.getDepTime(),LocalDateTime.now())) {
            throw new ReservationNotAllowedException();
        }

        return SeatBusResponseDto.of(bus);
    }

    private boolean canMakeReservation(String busDepTime, LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime depDateTime = LocalDateTime.parse(busDepTime, formatter);
        LocalDateTime closeTime = depDateTime.minusMinutes(10); //출발시간 10분전까지만 예약이 가능
        return now.isBefore(closeTime);
    }
}
