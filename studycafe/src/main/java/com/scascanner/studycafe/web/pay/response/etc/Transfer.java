package com.scascanner.studycafe.web.pay.response.etc;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transfer {

    private String bankCode;    // 추후 리펙토링

    @JsonValue
    private SettlementStatus settlementStatus;
}
