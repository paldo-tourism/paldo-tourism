package com.estsoft.paldotourism.dto.bus;

import com.estsoft.paldotourism.entity.Bus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BusInfoFindResponseDto {

    private Long busId;
    private String depTerminal;
    private String arrTerminal;
    private String depDate;
    private String busGrade;
    private String depTime;
    private String arrTime;
    private Integer charge;
    //TODO 찜 테이블 고려해서 변경
    private boolean isLike;
    private Integer remainingSeats; //남은 좌석 수
    private Integer totalSeatNumber; //총 좌석 수
    private boolean canReservation;


    public static BusInfoFindResponseDto of(Bus bus, int remainingSeats,LocalDateTime now,boolean isLike) {
        boolean canReserve = canMakeReservation(bus.getDepTime(),now);
        return BusInfoFindResponseDto.builder()
            .busId(bus.getId())
            .depTerminal(bus.getDepTerminal())
            .arrTerminal(bus.getArrTerminal())
            .depDate(formatDate(bus.getDepDate()))
            .busGrade(bus.getBusGrade())
            .depTime(formatTime(bus.getDepTime()))
            .arrTime(formatTime(bus.getArrTime()))
            .charge(bus.getCharge())
            .isLike(isLike)
            .remainingSeats(remainingSeats)
            .totalSeatNumber(bus.getTotalSeatNumber())
            .canReservation(canReserve)
            .build();
    }

    private static String formatDate(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.E");
        return LocalDate.parse(date,inputFormatter).format(outputFormatter);
    }

    private static String formatTime(String dateTime) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalDateTime.parse(dateTime,inputFormatter).format(outputFormatter);
    }

    private static boolean canMakeReservation(String busDepTime,LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime depDateTime = LocalDateTime.parse(busDepTime, formatter);
        LocalDateTime closeTime = depDateTime.minusMinutes(10); //출발시간 10분전까지만 예약이 가능
        return now.isBefore(closeTime);
    }
}
