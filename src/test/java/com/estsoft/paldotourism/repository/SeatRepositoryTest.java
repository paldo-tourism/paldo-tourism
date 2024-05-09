package com.estsoft.paldotourism.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.SeatStatus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BusRepository busRepository;

    @DisplayName("버스와 상태값을 입력 받아서 특정 버스의 좌석이 특정 상태값인 좌석들의 개수를 반환한다.")
    @Test
    void countByBusAndStatus() {
      //given
        Bus bus1 = createBus("서울경부","부산","20240508","202405081720","202405082120",45,26700,"일반");
        busRepository.save(bus1);

        createSeatsWithBusAndStatus(bus1,1,40,SeatStatus.EMPTY);
        createSeatsWithBusAndStatus(bus1,41,45,SeatStatus.SELECTED);

      //when
        Integer emptySeats = seatRepository.countByBusAndStatus(bus1,SeatStatus.EMPTY);

      //then
        assertThat(emptySeats).isEqualTo(40);
    }

    @DisplayName("입력된 버스ID의 좌석들을 모두 찾는다.")
    @Test
    void findByBusId() {
      //given
        Bus bus1 = createBus("서울경부","부산","20240508","202405081720","202405082120",45,26700,"일반");
        Bus bus2 = createBus("서울경부","강릉","20240507","202405071720","202405072120",45,16800,"일반");
        busRepository.saveAll(List.of(bus1,bus2));

        createSeatsWithBusAndStatus(bus1,1,45,SeatStatus.EMPTY);

      //when
        List<Seat> seats = seatRepository.findByBusId(bus1.getId());

      //then
        assertThat(seats).hasSize(45);
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