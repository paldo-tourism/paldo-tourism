package com.estsoft.paldotourism.dto.bus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusInfoFindRequestDto {

    private String depTerminalName;

    private String arrTerminalName;

    private String depDate;

    private String busGrade;

}
