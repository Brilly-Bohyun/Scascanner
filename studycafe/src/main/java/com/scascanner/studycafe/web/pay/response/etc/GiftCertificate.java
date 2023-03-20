package com.scascanner.studycafe.web.pay.response.etc;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class GiftCertificate {

    @Length(max = 8, message = "결제 승인번호는 최대 8자 입니다.")
    private String approveNo;

    @JsonValue
    private SettlementStatus settlementStatus;
}
