package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.bus.BusTerminalResponseDto;
import com.estsoft.paldotourism.entity.BusTerminal;
import com.estsoft.paldotourism.repository.BusTerminalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusTerminalService {

    private final BusTerminalRepository busTerminalRepository;

    public List<BusTerminalResponseDto> getTerminalsByRegion(String regionName) {
        List<BusTerminal> resultTerminalList = new ArrayList<>();

        if (regionName.equals("전체")) {
            resultTerminalList = busTerminalRepository.findAll();
        } else {
            resultTerminalList = busTerminalRepository.findByRegion(regionName);
        }

        return resultTerminalList.stream().map(BusTerminalResponseDto::of).collect(
            Collectors.toList());
    }

    public List<BusTerminalResponseDto> searchByTerminalName(String terminalName) {
        List<BusTerminal> resultTerminalList = busTerminalRepository.findByNameContaining(terminalName);

        if(resultTerminalList.isEmpty()) {
            log.info("해당 터미널은 존재하지 않습니다. 입력한 터미널: {}" , terminalName);
            throw new IllegalArgumentException("해당 터미널은 존재하지 않는 터미널입니다.");
        }

        return resultTerminalList.stream().map(BusTerminalResponseDto::of).collect(Collectors.toList());
    }
}
