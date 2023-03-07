package com.scascanner.studycafe.web.pay.dto.toss.response;

import lombok.Data;

@Data
public class TossPayResponseReady {

    private int code;
    private String checkoutPage;
    private String payToken;
    private String msg;
    private String errorCode;
}
