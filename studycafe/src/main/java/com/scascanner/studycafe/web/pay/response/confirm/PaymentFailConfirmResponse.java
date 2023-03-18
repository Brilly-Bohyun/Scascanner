package com.scascanner.studycafe.web.pay.response.confirm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailConfirmResponse {
    private String code;
    private String message;
    private String orderId;
}
