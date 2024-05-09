package com.estsoft.paldotourism.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.entity.BusTerminal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class BusTerminalRepositoryTest {

    @Autowired
    private BusTerminalRepository busTerminalRepository;

    @DisplayName("터미널 이름이 입력으로 주어지면 DB에 해당하는 터미널이 있는지 찾는다.")
    @Test
    void findByName() {
      //given
        String terminalName = "동대구";

      //when
        Optional<BusTerminal> result = busTerminalRepository.findByName(terminalName);

      //then
        assertThat(result.get().getName()).isEqualTo(terminalName);
    }

    @DisplayName("사용자가 입력한 지역에 해당하는 터미널을 찾는다.")
    @Test
    void findByRegion() {
      //given
        String terminalName = "서울 고속버스터미널";

      //when
        List<BusTerminal> result = busTerminalRepository.findByRegion(terminalName);

      //then
        assertThat(result).hasSize(0);
    }

    @DisplayName("사용자가 검색한 단어가 들어가있는 터미널을 찾는다.")
    @Test
    void findByNameContaining() {
      //given
        String searchTerminalName = "세종";

      //when
        List<BusTerminal> result = busTerminalRepository.findByNameContaining(searchTerminalName);

      //then
        assertThat(result).hasSize(4)
            .extracting("name")
            .containsExactlyInAnyOrder(
                "세종국무조정실", "세종시청", "세종연구단지", "세종청사"
            );
    }
}