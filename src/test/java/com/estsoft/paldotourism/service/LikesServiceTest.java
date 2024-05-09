package com.estsoft.paldotourism.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Likes;
import com.estsoft.paldotourism.entity.Role;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.exception.likes.AlreadyLikedException;
import com.estsoft.paldotourism.exception.likes.NotLikedYetException;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.LikesRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class LikesServiceTest {

    @Autowired
    private LikesService likesService;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        likesRepository.deleteAllInBatch();
        busRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("사용자가 특정 노선을 찜했을때 이미 해당 찜이 저장되어있는 경우")
    @Test
    void addLikeException() {
      //given
        User user1 = new User("test1@test","test1","test","01000000000", Role.ROLE_USER);
        Bus bus1 = createBus("서울경부","부산","20240509","202405091720","202405092120",45,26700,"일반");
        userRepository.save(user1);
        busRepository.save(bus1);
        Likes newLike = Likes.builder()
            .user(user1)
            .bus(bus1)
            .build();
        likesRepository.save(newLike);

      //when,then
        assertThatThrownBy(() ->  likesService.addLike(user1,bus1.getId()))
            .isInstanceOf(AlreadyLikedException.class);
    }

    @DisplayName("사용자가 특정 노선을 찜을 취소했을 때 해당 찜이 이미 저장되어있지 않은 경우")
    @Test
    void cancelLikeTest() {
      //given
        User user1 = new User("test1@test","test1","test","01000000000", Role.ROLE_USER);
        Bus bus1 = createBus("서울경부","부산","20240509","202405091720","202405092120",45,26700,"일반");
        userRepository.save(user1);
        busRepository.save(bus1);

      //when,then
        assertThatThrownBy(() ->  likesService.cancelLike(user1,bus1.getId()))
            .isInstanceOf(NotLikedYetException.class);
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