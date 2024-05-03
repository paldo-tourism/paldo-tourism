package com.estsoft.paldotourism.dto.openapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiResponseItems {
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<OpenApiResponseBusItem> item;
}
