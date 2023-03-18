package com.scascanner.studycafe.web.pay.service;

public interface PaymentService<T> {
    String payReady();
    T payInfo(String token);
}
