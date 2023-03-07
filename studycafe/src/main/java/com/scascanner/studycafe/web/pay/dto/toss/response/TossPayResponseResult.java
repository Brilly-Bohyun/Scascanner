package com.scascanner.studycafe.web.pay.dto.toss.response;

import lombok.Data;

// https://tossdev.github.io/api.html#httpResponse 참고
@Data
public class TossPayResponseResult {

    private String status;
    private String payToken;
    private String orderNo;
    private String payMethod;
    private Integer amount;
    private Integer discountedAmount;
    private Integer paidAmount;
    private String paidTs;
    private String transactionId;
    private Integer cardCompanyCode;
    private String cardAuthorizationNo;
    private String spreadOut;
    private boolean noInterest;
    private String cardMethod;
    private String cardNumber;
}
