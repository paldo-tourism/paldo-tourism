package com.estsoft.paldotourism.dto.bus;

import com.estsoft.paldotourism.entity.BusTerminal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusTerminalResponseDto {

    private String id;
    private String name;

    public static BusTerminalResponseDto of(BusTerminal busTerminal) {
        return BusTerminalResponseDto.builder()
            .id(busTerminal.getId())
            .name(busTerminal.getName())
            .build();
    }
}
