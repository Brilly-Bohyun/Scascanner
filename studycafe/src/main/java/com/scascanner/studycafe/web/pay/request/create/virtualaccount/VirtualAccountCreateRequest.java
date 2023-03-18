package com.scascanner.studycafe.web.pay.request.create.virtualaccount;

import com.scascanner.studycafe.web.pay.request.create.CommonPaymentCreateRequestInfo;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

public class VirtualAccountCreateRequest extends CommonPaymentCreateRequestInfo {

    @ApiModelProperty(value = "문화비(도서, 공연 티켓, 박물관·미술관 입장권 등) 지출 여부")
    private boolean cultureExpense;

    @ApiModelProperty(
            value = "가상계좌가 유효한 시간",
            notes = "값을 넣지 않으면 기본값 168시간(7일)으로 설정됩니다. 설정할 수 있는 최대값은 720시간(30일)입니다.\n" +
                    "validHours와 dueDate 중 하나만 사용할 수 있습니다."
    )
    private Integer validHours;

    @ApiModelProperty(
            value = "입금 기한",
            notes = "현재 시간을 기준으로 720시간(30일) 이내의 특정 시점으로 입금 기한을 직접 설정하고 싶을 때 사용합니다.\n" +
                    "validHours와 dueDate 중 하나만 사용할 수 있습니다."
    )
    private LocalDateTime dueDate;

    @ApiModelProperty(
            value = "고객의 휴대폰 번호",
            notes = "가상계좌 입금 안내가 전송되는 번호"
    )
    private String customerMobilePhone;

    @ApiModelProperty(
            value = "결제창에서 휴대폰 번호 입력 필드를 보여줄 지 여부",
            notes = "false를 넘기면 가상계좌 결제창에서 휴대폰 번호 입력 필드를 보여주지 않습니다. 기본값은 true 입니다."
    )
    private boolean showCustomerMobilePhone;

    @ApiModelProperty(value = "현금영수증 발급 정보를 담는 객체")
    private CashReceiptType cashReceiptType;

    @ApiModelProperty(value = "에스크로 사용 여부")
    private boolean useEscrow;

    @ApiModelProperty(
            value = "각 상품의 상세 정보를 담는 배열",
            notes = "에스크로 결제를 사용할 때만 필요한 파라미터입니다."
    )
    private List<EscrowProduct> escrowProducts;

    @ApiModelProperty(
            value = "가상계좌로 결제했을 때 고객에게 환불해 줄 계좌 정보",
            notes = "결제 승인을 요청한 뒤 돌아온 응답 객체에서 이 정보를 저장해서 사용할 수 있습니다. 응답 정보는 결제창 세션이 유효한 30분 동안에만 조회할 수 있습니다."
    )
    private RefundReceiveAccount refundReceiveAccount;
}
