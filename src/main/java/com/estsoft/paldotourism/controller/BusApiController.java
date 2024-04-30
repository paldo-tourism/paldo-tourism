package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.bus.BusInfoFindRequestDto;
import com.estsoft.paldotourism.dto.bus.BusInfoFindResponseDto;
import com.estsoft.paldotourism.service.BusApiService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class BusApiController {

    private final BusApiService busApiService;

    //메인화면에서 사용자가 출발지/목적지/출발날짜/버스등급을 입력
    @PostMapping("/api/bus")
    public ResponseEntity<?> getBusInfo(@Valid @RequestBody BusInfoFindRequestDto request, Model model) {
        List<BusInfoFindResponseDto> busList = busApiService.getBusInfo(request);

        model.addAttribute("schedules",busList);

//        return "/reservation/timeTable";
        return ResponseEntity.ok(busList);
    }
}
