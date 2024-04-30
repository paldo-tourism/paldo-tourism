package com.estsoft.paldotourism.dto.bus;

import com.estsoft.paldotourism.entity.Bus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    //TODO 남은 좌석 수 처리?
    private Integer remainingSeats; //남은 좌석 수
    private Integer totalSeatNumber; //총 좌석 수


    public static BusInfoFindResponseDto of(Bus bus, int remainingSeats) {
        return BusInfoFindResponseDto.builder()
            .busId(bus.getId())
            .depTerminal(bus.getDepTerminal())
            .arrTerminal(bus.getArrTerminal())
            .depDate(bus.getDepDate())
            .busGrade(bus.getBusGrade())
            .depTime(bus.getDepTime())
            .arrTime(bus.getArrTime())
            .charge(bus.getCharge())
            .remainingSeats(remainingSeats)
            .totalSeatNumber(bus.getTotalSeatNumber())
            .build();
    }
}
