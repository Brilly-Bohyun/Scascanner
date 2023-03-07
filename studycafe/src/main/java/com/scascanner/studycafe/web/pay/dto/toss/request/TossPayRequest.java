package com.scascanner.studycafe.web.pay.dto.toss.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
// https://tossdev.github.io/api.html#httpResponse 참고
public class TossPayRequest {

    private String apiKey;  // 가맹정 key (필수)
    private String orderNo; // 가맹자의 상품 주문 번호 (필수)
    private String productDesc; // 상품 설명 (필수)
    private String retUrl;  // 구매자 인증완료 후 연결할 가맹점 웹페이지 URL (필수)
    private String retCancelUrl;    // 토스페이창에 진입한 사용자가 결제를 중단할때 사용자를 이동시킬 가맹점 취소 페이지 (필수)
    private String retAppScheme;    // 결제 완료 후 연결할 가맹점 측 앱 스킴 값
    private boolean autoExecute;    // 자동 승인 여부 설정
    private String resultCallback;  // 결제 결과 callback URL (autoExecute 값이 true인 경우 필수)
    private String callbackVersion;    // 결제 결과 callback 버전  (autoExecute 값이 true인 경우 필수)
    private Integer amount; // 총 결제 금액 (필수)
    private Integer amountTaxFree;  // 결제 금액 중 비과세금액 (필수)
    private Integer amountTaxable;  // 결제 금액 중 과세 금액
    private Integer amountVat;  // 결제 금액 중 부가세
    private Integer amountServiceFee;   // 결제 금액 중 봉사료
    private String expiredTime; // 결제 만료 기간 (기본 값 10분, 최대 60분 설정 가능)
    private String enablePayMethods;    // 결제 수단 구분 변수
    private boolean cashReceipt;    // 현금영수증 발급 가능 여부
    private CashReceiptTradeOption cashReceiptTradeOption;  // 현금 영수증 발급 타입
    private MultiValueMap<String, String> cardOptions;  // 결제 창에 출력할특정 카드
    private Installment installment; // 할부 제한 타입

    @Builder
    public TossPayRequest(String apiKey, String orderNo, String productDesc, String retUrl, String retCancelUrl, boolean autoExecute, String resultCallback, Integer amount, Integer amountTaxFree) {
        this.apiKey = apiKey;
        this.orderNo = orderNo;
        this.productDesc = productDesc;
        this.retUrl = retUrl;
        this.retCancelUrl = retCancelUrl;
        this.autoExecute = autoExecute;
        this.resultCallback = resultCallback;
        this.amount = amount;
        this.amountTaxFree = amountTaxFree;
    }
}
