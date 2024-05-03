package com.estsoft.paldotourism.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorForm {

    private int code;
    private String message;
}
