package com.scascanner.studycafe.web.pay.service;

import com.scascanner.studycafe.web.pay.PaymentMethod;
import com.scascanner.studycafe.web.pay.request.dto.ReservationRequestPay;
import com.scascanner.studycafe.web.pay.request.create.PaymentCreateRequest;

import com.scascanner.studycafe.web.pay.request.dto.RoomRequestPay;
import com.scascanner.studycafe.web.pay.request.dto.UserRequestPay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    public PaymentCreateRequest createPaymentRequest(ReservationRequestPay reservationRequestPay, PaymentMethod method) {
        RoomRequestPay roomRequestPay = reservationRequestPay.getRoomRequestPay();
        UserRequestPay userRequestPay = reservationRequestPay.getUserRequestPay();

        String cafeName = reservationRequestPay.getStudyCafeRequestPay().getName();
        String roomName = roomRequestPay.getName();

        String orderName = cafeName + " " + roomName + "룸";

        String customerName = userRequestPay.getName();
        String customerEmail = userRequestPay.getEmail();
        int amount = roomRequestPay.getPrice() * (int) Duration.between(reservationRequestPay.getEndTime(), reservationRequestPay.getStartTime()).toHours();

        return PaymentCreateRequest.builder()
                .method(method)
                .orderId(UUID.randomUUID().toString())
                .amount(amount)
                .orderName(orderName)
                .customerEmail(customerEmail)
                .customerName(customerName)
                .build();
    }
}
