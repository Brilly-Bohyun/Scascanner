package com.scascanner.studycafe.web.pay.response.create;

import com.scascanner.studycafe.domain.entity.payment.Payment;
import com.scascanner.studycafe.web.pay.PaymentMethod;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CommonPaymentCreateRequest 클래스로 대체
@Data
public class PaymentCreateResponse {

    private PaymentMethod method;
    private Integer amount;
    private String orderId;
    private String orderName;
    private String customerEmail;
    private String customerName;
    private String successUrl;
    private String failUrl;
    private String createDate;
    private String paySuccessYn;

    @Builder
    public PaymentCreateResponse(PaymentMethod method, Integer amount, String orderId, String orderName, String customerEmail, String customerName, String successUrl, String failUrl, String createDate, String paySuccessYn) {
        this.method = method;
        this.amount = amount;
        this.orderId = orderId;
        this.orderName = orderName;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.successUrl = successUrl;
        this.failUrl = failUrl;
        this.createDate = createDate;
        this.paySuccessYn = paySuccessYn;
    }

    public static PaymentCreateResponse of(Payment payment) {
        return PaymentCreateResponse.builder()
                .method(payment.getMethod())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .customerEmail(payment.getCustomerEmail())
                .customerName(payment.getCustomerName())
                .successUrl("http://localhost:8080/payment/confirm")
                .failUrl("http://localhost:8080/payment/fail")
                .build();
    }
}
