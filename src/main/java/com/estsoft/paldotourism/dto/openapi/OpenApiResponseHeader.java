package com.estsoft.paldotourism.dto.openapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiResponseHeader {
    private String resultCode;
    private String resultMsg;
}
