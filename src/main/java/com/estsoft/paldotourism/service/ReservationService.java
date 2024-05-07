package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.dto.reservation.ReservationRequestDto;
import com.estsoft.paldotourism.entity.*;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.ReservationRepository;
import com.estsoft.paldotourism.repository.SeatRepository;
import com.estsoft.paldotourism.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BusRepository busRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;


    public ReservationService(ReservationRepository reservationRepository, BusRepository busRepository, SeatRepository seatRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.busRepository = busRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    // 예약 추가(예약 데이터를 테이블에 추가함)
    public Long addReservation(Long busId, ReservationRequestDto requestDto, String userName) {

        // 필요한 정보 가져옴
        Bus bus = busRepository.findById(busId).get();
        User user = userRepository.findByEmail(userName).get();
        
        // 예약 코드 생성 함수 호출
        String testReservationCode = generateReservationCode();
        
        // DTO를 엔티티 타입으로 변환 후 테이블에 추가
        Reservation reservation = requestDto.toEntity(bus, user, testReservationCode);
        Reservation saveReservation = reservationRepository.save(reservation);


        // 선택한 좌석들을 가져와 상태를 선택중으로 변경
        for (int i = 0; i < requestDto.getSeatNumbers().size(); i++) {
            Integer seat = requestDto.getSeatNumbers().get(i);

            updateSeatReservationStatus(bus, seat, SeatStatus.SELECTED);
            updateSeatReservation(reservation,bus,seat);

        }

        return saveReservation.getId();
    }

    // 예약 ID로 예약 1개 반환
    public Reservation showOneReservation(Long reservationId)
    {
        return reservationRepository.findById(reservationId).get();
    }

    // 유저로 모든 예약 반환
    public List<Reservation> showAllReservation(String userName)
    {
        // 필요한 정보 가져옴
        User user = userRepository.findByEmail(userName).get();

        return reservationRepository.findAllByUser(user);
    }


    // 예약 변경(기존 예약 데이터의 예약 상태를 취소로 바꾸고 새로운 예약 데이터를 테이블에 추가함)
    @Transactional
    public void changeReservation(Long reservationId, Long busId, ReservationRequestDto requestDto, String userName) {

        // 기존 예약 상태를 취소로 변경
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.updateReservationStatus(Status.STATUS_CANCEL);

        // 기존 예약의 좌석 상태를 빈 자리로 변경
        Bus reservationedBus = reservation.getBus();
        List<Seat> seatList = seatRepository.findAllByBus(reservationedBus);

        for (Seat seat : seatList) {
            Integer seatNumber = seat.getSeatNumber();
            updateSeatReservationStatus(reservationedBus, seatNumber, SeatStatus.EMPTY);
        }


        // 예약 변경(실제로는 예약 테이블에 새로운 예약 데이터를 추가하는 것과 동일함)
        Bus bus = busRepository.findById(busId).get();
        User user = userRepository.findByEmail(userName).get();

        // 예약 코드 생성 함수 호출
        String testReservationCode = generateReservationCode();

        // DTO를 엔티티 타입으로 변환 후 테이블에 추가
        Reservation changeReservation = requestDto.toEntity(bus,user,testReservationCode);
        reservationRepository.save(changeReservation);

        // 선택한 좌석들을 가져와 상태를 선택중으로 변경
        for (int i = 0; i < requestDto.getSeatNumbers().size(); i++) {
            Integer seat = requestDto.getSeatNumbers().get(i);

            updateSeatReservationStatus(bus, seat, SeatStatus.SELECTED);

        }

    }

    // 예약 취소(기존 예약 데이터의 예약 상태를 취소로 변경)
    @Transactional
    public void cancelReservation(Long reservationId) {

        // 예약 상태를 취소로 변경
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.updateReservationStatus(Status.STATUS_CANCEL);


        // 예약의 좌석 상태를 빈 자리로 변경
        Bus reservationedBus = reservation.getBus();
        List<Seat> seatList = seatRepository.findAllByBus(reservationedBus);


        for (Seat seat : seatList) {
            Integer seatNumber = seat.getSeatNumber();
            updateSeatReservationStatus(reservationedBus, seatNumber, SeatStatus.EMPTY);
        }
    }

    

    // 예약 ID로 해당 예약으로 예약한 좌석 리스트 반환
    public List<Seat> showAllSeatByReservation(Long reservationId)
    {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        return seatRepository.findAllByReservation(reservation);

    }

    // 버스 ID로 버스에 해당하는 좌석 리스트 반환
    public List<Seat> showAllSeatByBus(Long busId)
    {
        Bus bus = busRepository.findById(busId).get();

        return seatRepository.findAllByBus(bus);
    }



    // 좌석 상태 업데이트
    @Transactional
    public void updateSeatReservationStatus(Bus bus, Integer seatNumber, SeatStatus seatStatus) {
        Seat seat = seatRepository.findByBusAndSeatNumber(bus, seatNumber);
        seat.updateStatus(seatStatus);
    }

    // 좌석 예약 Id 업데이트
    @Transactional
    public void updateSeatReservation(Reservation reservation, Bus bus, Integer seatNumber)
    {
        Seat seat = seatRepository.findByBusAndSeatNumber(bus, seatNumber);
        seat.updateReservation(reservation);
    }



    // 예약 코드 생성
    public String generateReservationCode() {
        // 현재 날짜를 yyyymmdd 형식으로 포맷
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = sdf.format(new Date());
        Random random = new Random();

        // 6자리 난수 생성(난수가 중복되면 다시 생성)
        while(true)
        {
            int randomNumber = random.nextInt(900000) + 100000;  // 100000 ~ 999999 사이의 수

            // 날짜와 난수를 조합하여 최종 코드 생성
            String reservationCode = formattedDate + randomNumber;


            // 이미 만들어진 코드이면 반복
            // 없는 코드라면 반환
            Optional<Reservation> reservation = reservationRepository.findByReservationNumber(reservationCode);
            if(!(reservation.isPresent()))
            {
                return reservationCode;
            }
        }
    }

    // 예약의 총 금액을 반환
    public Long getTotalCharge(Long reservationId) {

        Long totalCharge = 0L;

        Reservation reservation = reservationRepository.findById(reservationId).get();

        List<Seat> seatList = seatRepository.findAllByReservation(reservation);

        for(int i =0;i<seatList.size();i++)
        {
            totalCharge += reservation.getBus().getCharge();
        }

        return totalCharge;
    }
}
