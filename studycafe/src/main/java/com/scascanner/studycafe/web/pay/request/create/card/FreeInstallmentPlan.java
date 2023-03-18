package com.scascanner.studycafe.web.pay.request.create.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FreeInstallmentPlan {

    @ApiModelProperty(value = "할부를 적용할 카드사 코드")
    private String company;

    @ApiModelProperty(value = "무이자를 적용할 할부 개월 정보")
    private List<Integer> months;
}
