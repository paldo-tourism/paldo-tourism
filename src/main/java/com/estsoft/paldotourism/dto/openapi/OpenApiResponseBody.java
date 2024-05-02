package com.estsoft.paldotourism.dto.openapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiResponseBody {

    private OpenApiResponseItems items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
}
