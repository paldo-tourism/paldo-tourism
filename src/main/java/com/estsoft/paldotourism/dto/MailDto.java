package com.estsoft.paldotourism.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class MailDto {
    private String dep_terminal;
    private String arr_terminal;
    private String dep_time;
    private String arr_time;
    private int charge;
    private String bus_grade;
    private String message;

    //아래 항목 테스트 변수로 삭제 예정입니다:)
    private String address;
    private String title;
}
