package com.estsoft.paldotourism.dto.bus;

import com.estsoft.paldotourism.entity.Seat;
import com.estsoft.paldotourism.entity.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SeatResponseDto {

    private Long id;
    private Integer seatNumber;
    private SeatStatus status;

    public static SeatResponseDto of(Seat seat) {
        return SeatResponseDto.builder()
            .id(seat.getId())
            .seatNumber(seat.getSeatNumber())
            .status(seat.getStatus())
            .build();
    }
}
