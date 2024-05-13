package com.estsoft.paldotourism.dto.user;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.PaymentHistory;
import com.estsoft.paldotourism.entity.Status;
import com.estsoft.paldotourism.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReservationDto {

    private Long id; // 예약 아이디(PK)

    private User user; // 유저 정보(FK)

    private Bus bus; // 버스 정보(FK)

    private PaymentHistory paymentHistory; // 결제 정보(FK)

    private String reservationNumber; // 예약 번호

    private Status reservationStatus;

    private Boolean reservationTimeCheck;
}
