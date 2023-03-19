package com.scascanner.studycafe.web.pay.response.confirm;

import com.scascanner.studycafe.web.pay.response.etc.Discount;
import com.scascanner.studycafe.web.pay.response.etc.GiftCertificate;
import com.scascanner.studycafe.web.pay.response.etc.MobilePhone;
import com.scascanner.studycafe.web.pay.response.etc.Transfer;
import com.scascanner.studycafe.web.pay.response.etc.Checkout;
import com.scascanner.studycafe.web.pay.response.etc.EasyPay;
import com.scascanner.studycafe.web.pay.response.etc.Failure;
import com.scascanner.studycafe.web.pay.response.etc.Receipt;
import com.scascanner.studycafe.web.pay.response.etc.card.Card;
import com.scascanner.studycafe.web.pay.response.etc.cardreceipt.CashReceipt;
import com.scascanner.studycafe.web.pay.response.etc.virtualaccount.VirtualAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSuccessConfirmResponse {

    private String mId;

    private String version;

    private String paymentKey;

    private String orderId;

    private String orderName;

    private String currency;

//    @JsonValue
    private String method;

    private Integer totalAmount;

    private Integer balanceAmount;

    private Integer suppliedAmount;

    private Integer vat; // (totalAmount - taxFreeAmount) / 11;

    private String status;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss±hh:mm")
    private String requestAt;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss±hh:mm")
    private String approvedAt;

    private boolean useEscrow;

    private String lastTransactionKey;

    private boolean cultureExpense; // 계좌이체, 가상계좌를 사용할 때만 true

    private Integer taxFreeAmount;  // 일반 상점일때는 0으로 계산, . 면세 상점, 복합 과세 상점일 때만 면세 금액이 돌아옵니다.

    private Integer taxExemptionAmount; // 결제 금액 중 과세 제외 금액(컵 보증금 등)

//    @Nullable
//    private List<Object> cancels;   // 결제 취소 이력

//    private boolean isPartialCancelable;    // 부분 취소 가능 여부입니다. 이 값이 false이면 전액 취소만 가능합니다.

    private Card card;

    private VirtualAccount virtualAccount;

    private String secret;

    private MobilePhone mobilePhone;

    private GiftCertificate giftCertificate;

    private Transfer transfer;

    private Receipt receipt;

    private Checkout checkout;

    private EasyPay easyPay;

    private Locale country;

    private Failure failure;

    private String type;

    private CashReceipt cashReceipt;

    private Discount discount;
}
