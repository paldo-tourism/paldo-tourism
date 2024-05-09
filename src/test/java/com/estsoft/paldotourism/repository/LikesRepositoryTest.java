package com.estsoft.paldotourism.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Likes;
import com.estsoft.paldotourism.entity.Role;
import com.estsoft.paldotourism.entity.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class LikesRepositoryTest {

    @Autowired
    LikesRepository likesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BusRepository busRepository;

    @DisplayName("사용자가 특정 버스를 이미 찜 했는지 여부를 찾는다.")
    @Test
    void findByUserIdAndBusId() {
      //given
        User user1 = new User("test1@test","test1","test","01000000000", Role.ROLE_USER);
        Bus bus1 = createBus("서울경부","부산","20240509","202405091720","202405092120",45,26700,"일반");

        userRepository.save(user1);
        busRepository.save(bus1);

        Likes newLike = Likes.builder().user(user1).bus(bus1).build();
        likesRepository.save(newLike);

      //when
        Optional<Likes> result = likesRepository.findByUserIdAndBusId(user1.getId(),bus1.getId());

      //then
        assertThat(result).isPresent();
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