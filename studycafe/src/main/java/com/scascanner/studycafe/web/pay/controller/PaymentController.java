package com.scascanner.studycafe.web.pay.controller;

import org.springframework.ui.Model;

public interface PaymentController {

    String payView();
    String pay();
    void paySuccess(String token, Model model);
    String payFail();
    String payCancel();
}
