package com.estsoft.paldotourism.dto.bus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BusInfoFindRequestDto {

    //TODO validator를 이용한 유효성 검사
    @NotBlank(message = "출발지 입력은 필수입니다.")
    private String depTerminalName;

    @NotBlank(message = "도착지 입력은 필수입니다.")
    private String arrTerminalName;

    @NotBlank(message = "출발 날짜 입력은 필수입니다.")
    private String depDate;

    @NotBlank(message = "버스 등급 입력은 필수입니다.")
    private String busGrade;

}
