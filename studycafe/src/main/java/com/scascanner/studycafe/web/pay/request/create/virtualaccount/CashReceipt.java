package com.scascanner.studycafe.web.pay.request.create.virtualaccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CashReceipt {

    @ApiModelProperty(
            value = "현금영수증 발급 용도",
            notes = "현금영수증 발급 용도입니다. 소득공제, 지출증빙, 미발행 중 하나입니다. 소득공제, 지출증빙을 넣으면 해당 용도가 선택된 상태로 결제창이 열립니다. 선택된 용도는 고객이 결제창에서 바꿀 수 있습니다."
    )
    private CashReceiptType type;
}
