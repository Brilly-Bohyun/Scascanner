package com.scascanner.studycafe.web.pay.controller;

import com.scascanner.studycafe.web.pay.service.kakao.KakaoPaymentServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KakaoPaymentControllerImpl implements PaymentController{

    private final KakaoPaymentServiceImpl kakaoPayService;

    @Override
    @GetMapping("/kakaoPay")
    public String payView() {
        return "/pay/payment";
    }

    @Override
    @PostMapping("/kakaoPay")
    public String pay() {
        return "redirect:" + kakaoPayService.payReady();
    }

    @Override
    @GetMapping("/kakaoPaySuccess")
    public void paySuccess(@RequestParam("pg_token") String pgToken, Model model) {
        model.addAttribute("info", kakaoPayService.payInfo(pgToken));
    }

    @Override
    public String payFail() {
        return null;
    }

    @Override
    public String payCancel() {
        return null;
    }
}
