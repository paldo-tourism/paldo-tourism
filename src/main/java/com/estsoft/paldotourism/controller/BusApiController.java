package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.bus.BusInfoFindRequestDto;
import com.estsoft.paldotourism.dto.bus.BusInfoFindResponseDto;
import com.estsoft.paldotourism.dto.bus.BusTerminalResponseDto;
import com.estsoft.paldotourism.service.BusApiService;
import com.estsoft.paldotourism.service.BusTerminalService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BusApiController {

    private final BusApiService busApiService;
    private final BusTerminalService busTerminalService;

    //메인화면에서 사용자가 출발지/목적지/출발날짜/버스등급을 입력
    @PostMapping("/api/bus")
    public ResponseEntity<?> getBusInfo(@Valid @RequestBody BusInfoFindRequestDto request, HttpSession httpSession, Model model) {
        List<BusInfoFindResponseDto> busList = busApiService.getBusInfo(request);

        //세션에 사용자가 입력한 정보를 저장한다.
        httpSession.setAttribute("schedules",busList);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/timeTable")
    public String showBusTimeTable(HttpSession session,Model model) {
        List<BusInfoFindResponseDto> busList = (List<BusInfoFindResponseDto>) session.getAttribute("schedules");

        if(busList == null) {
            return "redirect:/";
        } else {
            model.addAttribute("schedules",busList);
        }

        return "/reservation/timeTable";
    }

    @GetMapping("/api/terminals")
    @ResponseBody
    public List<BusTerminalResponseDto> getTerminalsByRegion(@RequestParam("regionName") String regionName) {
        return busTerminalService.getTerminalsByRegion(regionName);
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<BusTerminalResponseDto> searchByTerminalName(@RequestParam("terminalName") String terminalName) {
        return busTerminalService.searchByTerminalName(terminalName);
    }
}
