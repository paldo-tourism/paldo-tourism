package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.bus.BusInfoFindRequestDto;
import com.estsoft.paldotourism.dto.bus.BusInfoFindResponseDto;
import com.estsoft.paldotourism.dto.openapi.OpenApiResponseBusItem;
import com.estsoft.paldotourism.dto.openapi.OpenApiResult;
import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.BusTerminal;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.SeatStatus;
import com.estsoft.paldotourism.exception.openapi.BusRouteNotFoundException;
import com.estsoft.paldotourism.exception.openapi.UrlNotValidException;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.BusTerminalRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusApiService {

    private final BusRepository busRepository;
    private final SeatRepository seatRepository;
    private final BusTerminalRepository busTerminalRepository;

    private static final int PREMIUM_BUS = 1;
    private static final int REGULAR_BUS = 2;
    private static final int PREMIUM_BUS_TOTAL_SEATS = 28;
    private static final int REGULAR_BUS_TOTAL_SEATS = 45;

    @Value("${apis.bus.key}") //openapi 버스 serviceKey
    private String apiKey;
    private RestTemplate restTemplate = new RestTemplate();


    @Transactional
    public List<BusInfoFindResponseDto> getBusInfo(BusInfoFindRequestDto request) {
        String depTerminalId = convertBusNameToBusId(request.getDepTerminalName()); //출발터미널 이름을 터미널ID로 변환(API요청 때문)
        String arrTerminalId = convertBusNameToBusId(request.getArrTerminalName()); //도착터미널 이름을 터미널ID로 변환(API요청 때문)

        //사용자의 버스 request가 DB에 저장이 되어있는지 확인
        List<Bus> busInfoList = busRepository.findAllByDepTerminalAndArrTerminalAndDepDateAndBusGrade(
            request.getDepTerminalName(), request.getArrTerminalName(), request.getDepDate(),request.getBusGrade()
        );

        if(busInfoList.size() > 0) {//이미 테이블에 관련 데이터가 있다면 DB에 있던 버스데이터를 반환
            log.info("이미 존재하는 버스 데이터 입니다.");
            return busInfoList.stream().map(bus -> BusInfoFindResponseDto.of(bus,seatRepository.countByBusAndStatus(bus,SeatStatus.EMPTY),
                LocalDateTime.now())).collect(
                Collectors.toList());
        }

        //테이블에 관련 데이터가 없으므로 API request를 통해 버스 데이터를 가져온다
        OpenApiResult openApiResult = fetchBusDataFromApi(depTerminalId,arrTerminalId,request.getDepDate(),request.getBusGrade());

        List<OpenApiResponseBusItem> busItems = openApiResult.getResponse().getBody().getItems().getItem();
        return busItems.stream().map(item -> createBusWithSeats(item,request.getBusGrade(),request.getDepDate())).collect(
            Collectors.toList());
    }

    private BusInfoFindResponseDto createBusWithSeats(OpenApiResponseBusItem item, String busGrade, String depDate) {
//        int totalSeats = busGrade.equals("우등") ? PREMIUM_BUS_TOTAL_SEATS : REGULAR_BUS_TOTAL_SEATS;
        int totalSeats = REGULAR_BUS_TOTAL_SEATS;

        Bus bus = item.toEntity(depDate,busGrade,totalSeats);
        busRepository.save(bus);

        createSeatsForBus(bus,totalSeats);
        return BusInfoFindResponseDto.of(bus,seatRepository.countByBusAndStatus(bus,SeatStatus.EMPTY),LocalDateTime.now());
    }

    private void createSeatsForBus(Bus bus, int totalSeats) {
        for(int i = 1; i <= totalSeats; i++) {
            Seat seat = Seat.builder()
                .seatNumber(i)
                .bus(bus)
                .status(SeatStatus.EMPTY)
                .build();

            seatRepository.save(seat);
        }
    }


    private OpenApiResult fetchBusDataFromApi(String depTerminalId, String arrTerminalId, String depPlandTime, String busGrade) {
        int busGradeNumber = busGrade.equals("우등") ? PREMIUM_BUS : REGULAR_BUS;
        String url = buildUrl(depTerminalId,arrTerminalId,depPlandTime,busGradeNumber);

        ResponseEntity<String> apiResponse = callBusApi(url);

        return parseApiResponse(apiResponse.getBody());
    }

    private String buildUrl(String depTerminalId, String arrTerminalId, String depPlandTime, int busGrade) {
        return String.format(
            "http://apis.data.go.kr/1613000/ExpBusInfoService/getStrtpntAlocFndExpbusInfo?serviceKey=%s&depTerminalId=%s&arrTerminalId=%s&depPlandTime=%s&busGradeId=%s&numOfRows=20&pageNo=1&_type=json",
            apiKey, depTerminalId, arrTerminalId, depPlandTime, busGrade);
    }

    private ResponseEntity<String> callBusApi(String url) {
        try {
            URI uri = new URI(url);
            return restTemplate.getForEntity(uri, String.class);
        } catch (URISyntaxException e) {
            throw new UrlNotValidException();
        } catch (RestClientException e) {//4xx, 5xx 에러
            throw new RuntimeException("API 호출에 실패했습니다.", e);
        }
    }

    private OpenApiResult parseApiResponse(String jsonBody){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonBody,OpenApiResult.class);
        } catch (JsonProcessingException e) {
            log.error("JSON Parsing에 실패했습니다. 해당 노선이 없습니다. " + e);
            throw new BusRouteNotFoundException();
        }
    }

    private String convertBusNameToBusId(String TerminalName) {
        Optional<BusTerminal> terminal = busTerminalRepository.findByName(TerminalName);

        if(terminal.isEmpty()) {
            throw new IllegalStateException("해당 터미널은 존재하지 않는 터미널입니다.");
        }

        return terminal.get().getId();
    }
}
