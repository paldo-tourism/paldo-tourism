package com.estsoft.paldotourism.service;

import static org.junit.jupiter.api.Assertions.*;

import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.BusTerminalRepository;
import com.estsoft.paldotourism.repository.LikesRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class BusApiServiceTest {

    @Mock
    private BusRepository busRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private BusTerminalRepository busTerminalRepository;
    @Mock
    private LikesRepository likesRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private BusApiService busApiService;

    @DisplayName("")
    @Test
    void test() {
      //given

      //when

      //then
    }
}