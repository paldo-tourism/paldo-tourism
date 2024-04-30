package com.estsoft.paldotourism.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeatStatus {

    EMPTY("빈 자리"),
    SELECTED("선택 중인 자리"),
    OCCUPIED("예약 된 자리");

    private final String text;
}
