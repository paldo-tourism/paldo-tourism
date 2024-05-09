package com.estsoft.paldotourism.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Getter
@Setter
@NoArgsConstructor
public class AlanDto {
    private String travelDate;
    private String travelStart;
    private String travelType;
    private String travelNumber;
    private String travelExpenses;

    public String makeDummy() {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(travelDate)
                .append("에 ")
                .append(travelStart)
                .append("에서 출발하는 여행을 찾고 있고, 여행 인원은 ")
                .append(travelNumber)
                .append("명 여행을 가서")
                .append(travelType)
                .append("을 하고싶어 여행 경비는 ")
                .append(travelExpenses)
                .append("정도를 생각하고 있어")
                .append("적절한 국내 여행지 중 도시 한 곳을 추천해주고 꼭 버스를 타고 가야하니까 고속터미널이 있는 도시를 추천해줘")
                .append("답변 마지막에는 버스터미널을 명시해주고, 해당 지역에 가서 유형에 맞는 가볼만한 곳 몇가지 말해줘 예를들어 내가 데이트를 선택하면 데이트 코스, 등산을 선택하면 유명한 산 같이");
        log.info(messageBuilder.toString());
        return messageBuilder.toString();
    }
}


