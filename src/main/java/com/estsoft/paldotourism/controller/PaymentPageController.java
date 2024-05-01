package com.estsoft.paldotourism.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class PaymentPageController {

    @GetMapping("/payment")
    public String getPayment(Model model, Authentication authentication)
    {

        return "/payment/payment";
    }

    @GetMapping("/paymentComplete")
    public String getPaymentComplete(Model model, Authentication authentication)
    {
        return "/payment/paymentComplete";
    }
}
