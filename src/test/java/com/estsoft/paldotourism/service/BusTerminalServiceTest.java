package com.estsoft.paldotourism.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.dto.bus.BusTerminalResponseDto;
import com.estsoft.paldotourism.exception.bus.TerminalNotFoundException;
import com.estsoft.paldotourism.repository.BusTerminalRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class BusTerminalServiceTest {

    @Autowired
    private BusTerminalService busTerminalService;

    @Autowired
    private BusTerminalRepository busTerminalRepository;

    @DisplayName("사용자가 입력한 지역에 해당하는 터미널들을 반환한다.")
    @Test
    void getTerminalsByRegion() {
      //given
        String regionName = "서울";

      //when
        List<BusTerminalResponseDto> result =  busTerminalService.getTerminalsByRegion(regionName);

      //then
        assertThat(result).hasSize(3)
            .extracting("name")
            .containsExactlyInAnyOrder("서울경부","센트럴시티(서울)","동서울");
    }

    @DisplayName("사용자가 터미널이름을 검색했을때 해당하는 터미널을 반환한다.")
    @Test
    void searchByTerminalName() {
      //given
        String terminalName = "부산";

      //when
        List<BusTerminalResponseDto> result = busTerminalService.searchByTerminalName(terminalName);

      //then
        assertThat(result).hasSize(2)
            .extracting("name")
            .containsExactlyInAnyOrder("부산","서부산(사상)");
    }

    @DisplayName("사용자가 DB에 없는 터미널을 검색했을때 예외가 발생한다.")
    @Test
    void searchByTerminalName2() {
      //given
        String terminalName = "안녕하세요";

      //when,then
        assertThatThrownBy(() -> busTerminalService.searchByTerminalName(terminalName))
            .isInstanceOf(TerminalNotFoundException.class);
    }
}