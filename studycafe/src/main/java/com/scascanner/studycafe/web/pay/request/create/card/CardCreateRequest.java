package com.scascanner.studycafe.web.pay.request.create.card;

import com.scascanner.studycafe.web.pay.PaymentMethod;
import com.scascanner.studycafe.web.pay.request.create.CommonPaymentCreateRequestInfo;
import com.scascanner.studycafe.web.pay.request.create.FlowMode;
import com.scascanner.studycafe.web.pay.request.create.WindowTarget;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CardCreateRequest extends CommonPaymentCreateRequestInfo {

    @ApiModelProperty(value = "걀제 금액 중 과세 제외 금액(컵 보증금 등)", notes = "값을 넣지 않으면 기본값인 0으로 설정됩니다. 카드 결제 또는 간편결제로 계좌이체할 때 사용하세요")
    private Integer taxExemptionAmount;

    @ApiModelProperty(
            value = "기본/간편 결제창 선택 여부",
            notes = "DEFAULT는 카드, 간편결제 수단이 있는 기본 결제창을 호출합니다. 기본값입니다.\n" +
                    "DIRECT는 앱카드 또는 간편결제사 결제창을 호출합니다. 앱카드를 호출하려면 cardCompany를 설정하세요. 간편결제사 결제창을 호출하려면 easyPay를 설정하세요. 앱카드 또는 간편결제사 결제창을 사용하면 고객에게 직접 토스페이먼츠 이용약관 동의를 받으세요."
    )
    private FlowMode flowMode;

    @ApiModelProperty(
            value = "카드사 코드",
            notes = "flowMode 값이 DEFAULT이면 설정한 카드사만 보이는 기본 결제창이 열립니다. flowMode 값이 DIRECT이면 설정한 카드사의 결제창이 열립니다."
    )
    private String cardCompany;

    @ApiModelProperty(
            value = "간편결제사 코드",
            notes = " flowMode 값이 DIRECT이면 설정한 간편결제사의 결제창이 열립니다."
    )
    private String easyPay;

    @ApiModelProperty(
            value = "해외 카드 결제 여부",
            notes = "값이 true면 해외카드 결제가 가능한 영문 결제창이 열립니다."
    )
    private boolean useInternationalCardOnly;

    @ApiModelProperty(
            value = "카드 결제의 할부 개월 수",
            notes = "결제 금액(amount)이 5만원 이상일 때만 사용할 수 있습니다.\n" +
                    "2부터 12사이의 값을 사용할 수 있고, 0이 들어가면 할부가 아닌 일시불로 결제됩니다.\n" +
                    "값을 넣지 않으면 결제창에서 전체 할부 개월 수를 선택할 수 있습니다."
    )
    private Integer cardInstallmentPlan;

    @ApiModelProperty(
            value = "카드 결제에서 선택할 수 있는 최대 할부 개월 수",
            notes = "결제 금액(amount)이 5만원 이상일 때만 사용할 수 있습니다.\n" +
                    "2부터 12사이의 값을 사용할 수 있고, 0이 들어가면 할부가 아닌 일시불로 결제됩니다. 만약 값을 6으로 설정한다면 결제창에서 일시불~6개월 사이로 할부 개월을 선택할 수 있습니다.\n" +
                    "할부 개월 수를 고정하는 cardInstallmentPlan와 같이 사용할 수 없습니다."
    )
    private Integer maxCardInstallmentPlan;

    @ApiModelProperty(
            value = "무이자 할부를 적용할 카드사 및 할부 개월 정보",
            notes = ". 이 파라미터에 포함된 정보와 고객이 선택한 카드사 및 할부 개월이 매칭되면 무이자 할부가 적용됩니다."
    )
    private List<FreeInstallmentPlan> freeInstallmentPlans;

    @ApiModelProperty(
            value = "카드로 결제할 때 설정하는 카드사 포인트 사용 여부",
            notes = "값을 주지 않거나 값이 false라면 사용자가 카드사 포인트 사용 여부를 결정할 수 있습니다. 이 값을 true로 주면 카드사 포인트 사용이 체크된 상태로 결제창이 열립니다."
    )
    private boolean useCardPoint;

    @ApiModelProperty(
            value = "앱카드 결제창 사용 여부",
            notes = "이 값을 true로 주면 카드사의 앱카드 결제창만 열립니다.\n" +
                    "국민, 농협, 롯데, 삼성, 신한, 우리, 현대 카드에만 true 값을 사용할 수 있습니다."
    )
    private boolean useAppCardOnly;

    @ApiModelProperty(
            value = "카드사의 할인 코드",
            notes = "flowMode 값이 DIRECT여야 합니다.\n" +
                    "카드사 혜택 조회 API로 적용할 수 있는 할인 코드의 목록을 조회하세요."
    )
    private String discountCode;

    @ApiModelProperty(
            value = "모바일 ISP 앱에서 상점 앱으로 돌아올 때 사용",
            notes = "상점의 앱 스킴을 지정하면 됩니다. 예를 들면 testapp://같은 형태입니다."
    )
    private String appScheme;

    @Builder
    public CardCreateRequest(PaymentMethod method, String orderId, Integer amount, Integer taxFreeAmount, String orderName, String customerEmail, String customerName, WindowTarget windowTarget, Integer taxExemptionAmount, FlowMode flowMode, String cardCompany, String easyPay, boolean useInternationalCardOnly, Integer cardInstallmentPlan, Integer maxCardInstallmentPlan, List<FreeInstallmentPlan> freeInstallmentPlans, boolean useCardPoint, boolean useAppCardOnly, String discountCode, String appScheme) {
        super(method, orderId, amount, taxFreeAmount, orderName, customerEmail, customerName, windowTarget);
        this.taxExemptionAmount = taxExemptionAmount;
        this.flowMode = flowMode;
        this.cardCompany = cardCompany;
        this.easyPay = easyPay;
        this.useInternationalCardOnly = useInternationalCardOnly;
        this.cardInstallmentPlan = cardInstallmentPlan;
        this.maxCardInstallmentPlan = maxCardInstallmentPlan;
        this.freeInstallmentPlans = freeInstallmentPlans;
        this.useCardPoint = useCardPoint;
        this.useAppCardOnly = useAppCardOnly;
        this.discountCode = discountCode;
        this.appScheme = appScheme;
    }
}
