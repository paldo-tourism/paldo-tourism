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
                .append("명 여행 유형은 ")
                .append(travelType)
                .append("여행 경비는 ")
                .append(travelExpenses)
                .append("정도를 생각하고 있어")
                .append("적절한 국내 여행지 중 고속터미널이 있는 지역을 하나만 추천해주고 해당 여행지와 가장 가까운 고속 터미널도 이 중에서 찾아줘, 답변형식에 맞춰서 답변해줘 ")
                .append("답변형식은 다음과 같은 조건에 맞춰서 부탁해 추천해준 목적지, 목적지와 가까운 고속 터미널, 해당 목적지를 추천해준 이유를 적는다. 출처는 모두 모아 맨 마지막에 붙인다.");

        log.info(messageBuilder.toString());
        return messageBuilder.toString();
    }
}


