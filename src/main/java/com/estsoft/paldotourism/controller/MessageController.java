package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Reservation;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.service.ReservationService;
import com.siot.IamportRestClient.IamportClient;
import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private DefaultMessageService messageService;
    private final ReservationService reservationService;

    @Value("${sms.api.key}")
    private String apiKey;

    @Value("${sms.api.secretkey}")
    private String secretKey;

    public MessageController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey,secretKey,"https://api.coolsms.co.kr");
    }


    //메시지 전송
    @PostMapping("/send-one/{reservationId}")
    public SingleMessageSentResponse sendOne(@PathVariable Long reservationId) {
        Message message = new Message();

        Reservation reservation = reservationService.showOneReservation(reservationId);


        User user = reservation.getUser();
        Bus bus = reservation.getBus();

        String content = "%s%s에 %s에서 %s로 가는 노선을 예약 하셨습니다".formatted(bus.getDepDate(), bus.getDepTime(), bus.getDepTerminal(), bus.getArrTerminal());


        // 발신번호 및 수신번호는 - 포함하지 않고 작성
        // 예시 : 01059201930
        message.setFrom("발신번호");
        message.setTo(user.getPhoneNumber());

        //한글 45자 이하 입력해야 SMS타입으로 메시지 추가됨
        //그 이상으로 입력하면 LMS타입이됨
        message.setText(content);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}
