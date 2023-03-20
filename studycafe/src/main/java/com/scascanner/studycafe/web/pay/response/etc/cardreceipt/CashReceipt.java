package com.scascanner.studycafe.web.pay.response.etc.cardreceipt;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashReceipt {

    private String receiptKey;

//    @JsonValue
    private String type;

    private Integer amount;

    private Integer taxFreeAmount;

    private String issueNumber;

    private String receiptUrl;
}
