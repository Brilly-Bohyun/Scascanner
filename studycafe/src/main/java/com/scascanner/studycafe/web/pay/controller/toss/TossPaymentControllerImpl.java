package com.scascanner.studycafe.web.pay.controller.toss;

import com.scascanner.studycafe.web.pay.controller.PaymentController;
import com.scascanner.studycafe.web.pay.service.toss.TossPaymentServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TossPaymentControllerImpl implements PaymentController {

    private final TossPaymentServiceImpl tossPaymentService;

    @Override
    @GetMapping("/tossPay")
    public String payView() {
        return "/pay/payment";
    }

    @Override
    @PostMapping("/tossPay")
    public String pay() {
        return "redirect:" + tossPaymentService.payReady();
    }

    @Override
    public void paySuccess(String token, Model model) {

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
