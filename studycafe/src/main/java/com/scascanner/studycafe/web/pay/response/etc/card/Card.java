package com.scascanner.studycafe.web.pay.response.etc.card;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {
    private Integer amount;
    private String issuerCode;
    private String acquirerCode;
    private String number;
    private Integer installmentPlanMonths;  // 할부 개월수, 무이자면 0
    private String approveNo;
    private String useCardPoint;
    private String receiptUrl;

//    @JsonValue
    private String  cardType;

//    @JsonValue
    private String  ownerType;

//    @JsonValue
    private String acquireStatus;

    private boolean isInterestFree; // 무이자 할부 적용 여부

//    @JsonValue
    private String interestPayer;
}
