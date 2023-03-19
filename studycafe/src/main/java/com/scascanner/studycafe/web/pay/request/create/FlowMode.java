package com.scascanner.studycafe.web.pay.request.create;

/**
 * 결제창 유형입니다.
 * - DEFAULT: 토스페이먼츠 결제창을 호출합니다. 기본값입니다.
 * - DIRECT: 카드사 또는 간편결제사 결제창을 호출합니다. cardCompany 또는 easyPay 파라미터를 설정하세요.
 * * flowMode 값이 DIRECT이면 상점에서 직접 고객에게 토스페이먼츠 이용약관 동의를 받아야 합니다. 자세한 내용은 앱카드 결제창 연동하기를 참고하세요.
 */
public enum FlowMode {
    DEFAULT, DIRECT
}
