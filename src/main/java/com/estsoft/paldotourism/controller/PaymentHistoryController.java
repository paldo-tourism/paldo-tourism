package com.estsoft.paldotourism.controller;

import com.estsoft.paldotourism.service.PaymentHistoryService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@Slf4j
public class PaymentHistoryController {

    private IamportClient iamportClient;

    private final PaymentHistoryService paymentHistoryService;

    //결제 상점키
    @Value("${imp.code}")
    private String storeCode;

    // 결제 api 키
    @Value("${imp.api.key}")
    private String apiKey;

    // 결제 api 비밀키
    @Value("${imp.api.secretkey}")
    private String secretKey;

    public PaymentHistoryController(PaymentHistoryService paymentHistoryService) {
        this.paymentHistoryService = paymentHistoryService;
    }

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }


    @ResponseBody
    @RequestMapping("/verify/{imp_uid}/{reservationId}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable("imp_uid") String imp_uid, @PathVariable("reservationId") Long reservationId)
            throws IamportResponseException, IOException {

        paymentHistoryService.createPaymentHistory(reservationId);


        log.info("결제 성공");

        return iamportClient.paymentByImpUid(imp_uid);
    }

}
