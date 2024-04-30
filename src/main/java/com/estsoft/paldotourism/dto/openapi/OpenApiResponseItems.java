package com.estsoft.paldotourism.dto.openapi;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiResponseItems {
    private List<OpenApiResponseBusItem> item;
}
