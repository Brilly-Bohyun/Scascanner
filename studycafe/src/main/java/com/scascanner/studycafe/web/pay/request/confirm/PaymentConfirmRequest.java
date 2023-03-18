package com.scascanner.studycafe.web.pay.request.confirm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentConfirmRequest {

    private String paymentKey;
    private String orderId;
    private Integer amount;
}