package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.dto.bus.BusInfoFindRequestDto;
import com.estsoft.paldotourism.dto.bus.BusInfoFindResponseDto;
import com.estsoft.paldotourism.dto.bus.BusTerminalResponseDto;
import com.estsoft.paldotourism.dto.bus.SeatBusResponseDto;
import com.estsoft.paldotourism.dto.bus.SeatResponseDto;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.BusApiService;
import com.estsoft.paldotourism.service.BusTerminalService;
import com.estsoft.paldotourism.service.SeatService;
import com.estsoft.paldotourism.service.UserDetailService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BusApiController {

    private final BusApiService busApiService;
    private final BusTerminalService busTerminalService;
    private final UserDetailService userDetailService;
    private final SeatService seatService;

    /*
    @GetMapping("")
    public String index() {
        return "index";
    }
     */

    //메인화면에서 사용자가 출발지/목적지/출발날짜/버스등급을 입력
    @GetMapping("/timeTable")
    public String showBusTimeTable(@RequestParam("depTerminalName") String depTerminalName,
        @RequestParam("arrTerminalName") String arrTerminalName, @RequestParam("depDate") String depDate,
        @RequestParam("busGrade") String busGrade, Model model) {
        BusInfoFindRequestDto request = new BusInfoFindRequestDto(depTerminalName, arrTerminalName, depDate, busGrade);
        Optional<User> currentUser = userDetailService.getCurrentUser();
        List<BusInfoFindResponseDto> busList = busApiService.getBusInfo(request, currentUser);

        if (busList.isEmpty()) {
            model.addAttribute("errorMessage", "해당 노선이 없습니다.");
            return "/reservation/timeTable";  // 버스 목록이 비어 있을 경우, 에러 메시지를 모델에 추가
        }

        model.addAttribute("schedules", busList);
        return "/reservation/timeTable";  // 타임 테이블 페이지 렌더링
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

    @GetMapping("/seatSelect")
    @PreAuthorize("isAuthenticated() or hasRole('ROLE_ADMIN')")
    public String showSeatSelect(@RequestParam("busId") Long busId,Model model) {
        List<SeatResponseDto> seats = seatService.getSeatsByBusId(busId);
        SeatBusResponseDto busInfo = seatService.getBusByBusId(busId);

        model.addAttribute("bus",busInfo);
        model.addAttribute("seats",seats);
        return "/reservation/seatSelect";
    }

}
