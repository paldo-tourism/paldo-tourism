package com.estsoft.paldotourism.dto.bus;

import com.estsoft.paldotourism.entity.Bus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SeatBusResponseDto {

    private Long busId;
    private String depTerminal;
    private String arrTerminal;
    private String depDate;
    private String busGrade;
    private String depTime;

    public static SeatBusResponseDto of(Bus bus) {
        return SeatBusResponseDto.builder()
            .busId(bus.getId())
            .depTerminal(bus.getDepTerminal())
            .arrTerminal(bus.getArrTerminal())
            .depDate(formatDate(bus.getDepDate()))
            .busGrade(bus.getBusGrade())
            .depTime(formatTime(bus.getDepTime()))
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
}
