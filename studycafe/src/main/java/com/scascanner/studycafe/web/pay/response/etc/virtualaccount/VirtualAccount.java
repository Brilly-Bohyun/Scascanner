package com.scascanner.studycafe.web.pay.response.etc.virtualaccount;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class VirtualAccount {

    @JsonValue
    private AccountType accountType;

    @Length(max = 20, message = "계좌번호는 최대 20자입니다.")
    private String accountNumber;

    private String bankCode;    // 이거는 추후 BankCode 만들 예정 https://docs.tosspayments.com/reference/codes#은행-코드

    @Length(max = 100, message = "가상계좌 발급 고객 이름은 최대 100자입니다.")
    private String customerName;

    private String dueDate;

    @JsonValue
    private RefundStatus refundStatus;

    private boolean expired;

}
