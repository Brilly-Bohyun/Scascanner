package com.scascanner.studycafe.web.pay.response.etc;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MobilePhone {

    private String customerMobilePhone;

    @JsonValue
    private SettlementStatus settlementStatus;
    private String receiptUrl;
}
