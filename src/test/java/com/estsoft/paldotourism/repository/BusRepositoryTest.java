package com.estsoft.paldotourism.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.entity.Bus;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class BusRepositoryTest {

    @Autowired
    private BusRepository busRepository;

    @DisplayName("출발/도착터미널,출발날짜,버스등급 즉 사용자의 request를 입력해 해당 버스 리스트가 DB에 있는지 확인한다.")
    @Test
    void findAllByDepTerminalAndArrTerminalAndDepDateAndBusGrade() {
      //given
        Bus bus1 = createBus("서울경부","부산","20240507","202405071720","202405072120",45,26700,"일반");
        Bus bus2 = createBus("서울경부","부산","20240508","202405081820","202405082220",45,26700,"일반");
        Bus bus3 = createBus("서울경부","부산","20240508","202405082040","202405090040",45,26700,"일반");
        Bus bus4 = createBus("서울경부","강릉","20240507","202405071720","202405072120",45,16800,"일반");
        Bus bus5 = createBus("서울경부","강릉","20240508","202405081720","202405082120",45,26700,"우등");

        busRepository.saveAll(List.of(bus1,bus2,bus3,bus4,bus5));

      //when
        List<Bus> busList = busRepository.findAllByDepTerminalAndArrTerminalAndDepDateAndBusGrade("서울경부","부산","20240508","일반");

      //then
        assertThat(busList).hasSize(2)
            .extracting("depTerminal","arrTerminal","depDate","busGrade","depTime")
            .containsExactlyInAnyOrder(
                tuple("서울경부","부산","20240508","일반","202405081820"),
                tuple("서울경부","부산","20240508","일반","202405082040")
            );
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
}