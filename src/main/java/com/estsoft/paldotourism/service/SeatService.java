package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.bus.SeatBusResponseDto;
import com.estsoft.paldotourism.dto.bus.SeatResponseDto;
import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.exception.bus.BusNotFoundException;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
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

        return SeatBusResponseDto.of(bus);
    }
}
