package com.estsoft.paldotourism.dto.openapi;

import com.estsoft.paldotourism.entity.Bus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpenApiResponseBusItem {
    private String routeId;
    private String gradeNm;
    private String depPlandTime;
    private String arrPlandTime;
    private String depPlaceNm;
    private String arrPlaceNm;
    private Integer charge;

    //api를 통해 받은 정보를 바탕으로 bus 엔티티를 생성하는 메서드
    public Bus toEntity(String depDate, String busGrade, Integer totalSeatNumber) {
        return Bus.builder()
            .depTerminal(depPlaceNm)
            .arrTerminal(arrPlaceNm)
            .depDate(depDate)
            .depTime(depPlandTime)
            .arrTime(arrPlandTime)
            .totalSeatNumber(totalSeatNumber)
            .charge(charge)
            .busGrade(busGrade)
            .build();
    }
}
