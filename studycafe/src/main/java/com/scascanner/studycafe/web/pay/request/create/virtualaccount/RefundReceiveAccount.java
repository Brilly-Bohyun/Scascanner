package com.scascanner.studycafe.web.pay.request.create.virtualaccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 가상계좌로 결제했을 때 고객에게 환불해 줄 계좌 정보입니다.
 * 결제 승인을 요청한 뒤 돌아온 응답 객체에서 이 정보를 저장해서 사용할 수 있습니다.
 * 응답 정보는 결제창 세션이 유효한 30분 동안에만 조회할 수 있습니다.
 */
@Data
@NoArgsConstructor
public class RefundReceiveAccount {

    @ApiModelProperty("고객 환불 계좌의 은행 코드")
    private String bankCode;

    @ApiModelProperty("고객 환불 계좌 번호")
    private String accountNumber;

    @ApiModelProperty("고객 환불 계좌의 예금주")
    private String holderName;
}
