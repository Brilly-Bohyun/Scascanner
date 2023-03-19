package com.scascanner.studycafe.web.pay.response.etc;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 간편 결제 정보
 */
@Data
@NoArgsConstructor
public class EasyPay {

    private String provider;
    private Integer amount;
    private Integer discountAmount;
}
