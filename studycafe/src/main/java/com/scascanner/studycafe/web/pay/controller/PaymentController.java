package com.scascanner.studycafe.web.pay.controller;

import com.scascanner.studycafe.web.pay.PaymentMethod;

import com.scascanner.studycafe.web.pay.request.confirm.PaymentConfirmRequest;
import com.scascanner.studycafe.web.pay.request.dto.ReservationRequestPay;
import com.scascanner.studycafe.web.pay.response.confirm.PaymentSuccessConfirmResponse;
import com.scascanner.studycafe.web.pay.response.create.PaymentCreateResponse;
import com.scascanner.studycafe.web.pay.service.PaymentService;
import com.scascanner.studycafe.web.pay.service.TossService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final TossService tossService;

    @GetMapping("/create")
    @ResponseBody
    public ResponseEntity<PaymentCreateResponse> create(Model model, @ModelAttribute ReservationRequestPay reservationRequestPay, @RequestParam PaymentMethod method) {

        PaymentCreateResponse paymentCreateResponse = tossService.create(paymentService.createPaymentRequest(reservationRequestPay, method));

        log.info("paymentCreateResponse={}", paymentCreateResponse);
        return new ResponseEntity<>(paymentCreateResponse, HttpStatus.OK);

        // 프론트에게 결제 관련 정보들을 전달
//        model.addAttribute("paymentResponse", paymentCreateResponse);
//        return "index";
    }

    /**
     * success url로 리다이렉트 시 최종 결제 정보 승인
     * @param paymentConfirmRequest 쿼리 파라미터 paymentKey, orderId, amount 가 담김
     * @return
     */
    @GetMapping("/success")
    @ResponseBody
    public ResponseEntity<PaymentSuccessConfirmResponse> successConfirm(@ModelAttribute PaymentConfirmRequest paymentConfirmRequest) {
        log.info("paymentFinalRequest={}", paymentConfirmRequest);

        return new ResponseEntity<>(tossService.confirm(paymentConfirmRequest), HttpStatus.OK);
    }

    /*
    @GetMapping("/fail")
    public ResponseEntity<PaymentSuccessConfirmResponse> failConfirm(@ModelAttribute PaymentConfirmRequest paymentConfirmRequest) {
        log.info("paymentConfirmRequest={}", paymentConfirmRequest);

        return new ResponseEntity<>(tossService.confirm(paymentConfirmRequest), HttpStatus.BAD_REQUEST);
    }

     */
    @GetMapping("/test")
    public String test() {
        return "index";
    }
}
