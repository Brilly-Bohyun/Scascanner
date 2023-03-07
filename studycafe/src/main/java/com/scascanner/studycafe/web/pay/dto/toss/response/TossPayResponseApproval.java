package com.scascanner.studycafe.web.pay.dto.toss.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class TossPayResponseApproval {

    private String mid;
    private LocalDate version;
    private String paymentKey;
    private TossPayStatus status;
    private String lastTransactionKey;
    private String orderId;
    private String orderName;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private boolean useEscrow;
    private boolean cultureExpense;
    private TossPayResponseCard card;
    private Map<String, String> receipt;
    private Map<String, String> checkout;
    private String currency;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private Integer vat;
    private Integer taxFreeAmount;
    private Integer taxExemptionAmount;
    private TossPayMethod method;
}
