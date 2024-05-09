package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.reservation.ReservationRequestDto;
import com.estsoft.paldotourism.entity.*;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.ReservationRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationServiceTest {


    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private BusRepository busRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addReservation() {
        // 가정 설정
        Long busId = 1L;
        String userName = "user@example.com";
        Bus bus = new Bus();
        User user = new User();
        ReservationRequestDto requestDto = new ReservationRequestDto();
        requestDto.setSeatNumbers(Arrays.asList(1, 2, 3)); // 가정한 좌석 번호들

        when(busRepository.findById(busId)).thenReturn(Optional.of(bus));
        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 테스트 실행
        reservationService.addReservation(busId, requestDto, userName);

        // 검증
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(seatRepository, times(requestDto.getSeatNumbers().size())).findByBusAndSeatNumber(any(Bus.class), anyInt());

    }

    @Test
    void showOneReservation() {
        Long reservationId = 1L;
        Reservation expectedReservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(expectedReservation));

        Reservation actualReservation = reservationService.showOneReservation(reservationId);

        assertNotNull(actualReservation);
        assertEquals(expectedReservation, actualReservation);
        verify(reservationRepository).findById(reservationId);
    }

    @Test
    void showAllReservation() {
        String userName = "user@example.com";
        User user = new User();
        List<Reservation> expectedReservations = Arrays.asList(new Reservation(), new Reservation());

        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));
        when(reservationRepository.findAllByUser(user)).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.showAllReservation(userName);

        assertNotNull(actualReservations);
        assertEquals(expectedReservations.size(), actualReservations.size());
        verify(reservationRepository).findAllByUser(user);
    }

    @Test
    void changeReservation() {
        Long oldReservationId = 1L, newBusId = 2L;
        String userName = "user@example.com";
        Reservation oldReservation = new Reservation();
        Bus newBus = new Bus();
        User user = new User();
        ReservationRequestDto requestDto = new ReservationRequestDto();

        when(reservationRepository.findById(oldReservationId)).thenReturn(Optional.of(oldReservation));
        when(busRepository.findById(newBusId)).thenReturn(Optional.of(newBus));
        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        reservationService.changeReservation(oldReservationId, newBusId, requestDto, userName);

        verify(reservationRepository).save(any(Reservation.class));  // Verify new reservation saved
        verify(oldReservation).updateReservationStatus(Status.STATUS_CANCEL);  // Verify old reservation canceled
    }

    @Test
    void cancelReservation() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.cancelReservation(reservationId);

        verify(reservation).updateReservationStatus(Status.STATUS_CANCEL);
    }

    @Test
    void showAllSeatByReservation() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        List<Seat> expectedSeats = Arrays.asList(new Seat(), new Seat());

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(seatRepository.findAllByReservation(reservation)).thenReturn(expectedSeats);

        List<Seat> actualSeats = reservationService.showAllSeatByReservation(reservationId);

        assertNotNull(actualSeats);
        assertEquals(expectedSeats.size(), actualSeats.size());
        verify(seatRepository).findAllByReservation(reservation);
    }

    @Test
    void showAllSeatByBus() {
        Long busId = 1L;
        Bus bus = new Bus();
        List<Seat> expectedSeats = Arrays.asList(new Seat(), new Seat());

        when(busRepository.findById(busId)).thenReturn(Optional.of(bus));
        when(seatRepository.findAllByBus(bus)).thenReturn(expectedSeats);

        List<Seat> actualSeats = reservationService.showAllSeatByBus(busId);

        assertNotNull(actualSeats);
        assertEquals(expectedSeats.size(), actualSeats.size());
        verify(seatRepository).findAllByBus(bus);
    }

    @Test
    void updateSeatReservationStatus() {
        Bus bus = new Bus();
        Integer seatNumber = 1;
        Seat seat = new Seat();

        when(seatRepository.findByBusAndSeatNumber(bus, seatNumber)).thenReturn(seat);

        reservationService.updateSeatReservationStatus(bus, seatNumber, SeatStatus.EMPTY);

        verify(seat).updateStatus(SeatStatus.EMPTY);
    }

    @Test
    void updateSeatReservation() {
        Reservation reservation = new Reservation();
        Bus bus = new Bus();
        Integer seatNumber = 1;
        Seat seat = new Seat();

        when(seatRepository.findByBusAndSeatNumber(bus, seatNumber)).thenReturn(seat);

        reservationService.updateSeatReservation(reservation, bus, seatNumber);

        verify(seat).updateReservation(reservation);
    }

    @Test
    void generateReservationCode() {
        String generatedCode = reservationService.generateReservationCode();
        assertNotNull(generatedCode);
        assertTrue(generatedCode.matches("\\d{14}")); // Checks if the generated code matches the expected pattern
    }

    @Test
    void getTotalCharge() {
        Long reservationId = 1L;
        Reservation reservation = new Reservation();
        Bus bus = new Bus();
        List<Seat> seats = Arrays.asList(new Seat(), new Seat(), new Seat());
//        bus.setCharge(100L); // 가정: 각 좌석의 비용은 100
//        reservation.setBus(bus);
        Long expectedTotalCharge = 300L; // 3 * 100

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(seatRepository.findAllByReservation(reservation)).thenReturn(seats);

        Long actualTotalCharge = reservationService.getTotalCharge(reservationId);

        assertEquals(expectedTotalCharge, actualTotalCharge);
    }
}