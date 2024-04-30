package com.estsoft.paldotourism.dto.openapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiResponse {
    private OpenApiResponseHeader header;
    private OpenApiResponseBody body;
}
