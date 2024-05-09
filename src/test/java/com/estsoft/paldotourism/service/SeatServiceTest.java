package com.estsoft.paldotourism.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.dto.bus.SeatResponseDto;
import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.SeatStatus;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
class SeatServiceTest {

    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BusRepository busRepository;

    @AfterEach
    void tearDown() {
        seatRepository.deleteAllInBatch();
        busRepository.deleteAllInBatch();
    }

    @DisplayName("bus ID로 해당 버스의 좌석들을 찾아 반환한다.")
    @Test
    void getSeatsByBusId() {
      //given
        Bus bus1 = createBus("서울경부","부산","20240508","202405081720","202405082120",45,26700,"일반");
        Bus bus2 = createBus("서울경부","강릉","20240507","202405071720","202405072120",45,16800,"일반");
        busRepository.saveAll(List.of(bus1,bus2));

        createSeatsWithBusAndStatus(bus1,1,45, SeatStatus.EMPTY);

      //when
        List<SeatResponseDto> result = seatService.getSeatsByBusId(bus1.getId());

      //then
        assertThat(result).hasSize(45);
    }

    private Bus createBus(String depTerminal,String arrTerminal,String depDate,String depTime,String arrTime,Integer totalSeatNumber,Integer charge,String busGrade) {
        return Bus.builder()
            .depTerminal(depTerminal)
            .arrTerminal(arrTerminal)
            .depDate(depDate)
            .depTime(depTime)
            .arrTime(arrTime)
            .totalSeatNumber(totalSeatNumber)
            .charge(charge)
            .busGrade(busGrade)
            .build();
    }

    private void createSeatsWithBusAndStatus(Bus bus, int startNum, int lastNum, SeatStatus status) {
        for(int i = startNum; i <= lastNum; i++) {
            Seat seat = Seat.builder()
                .seatNumber(i)
                .bus(bus)
                .status(status)
                .build();

            seatRepository.save(seat);
        }
    }
}