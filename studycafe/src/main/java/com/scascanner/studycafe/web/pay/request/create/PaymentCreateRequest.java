package com.scascanner.studycafe.web.pay.request.create;

import com.scascanner.studycafe.domain.entity.payment.Payment;
import com.scascanner.studycafe.web.pay.PaymentMethod;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentCreateRequest {
    @ApiModelProperty("지불 방법")
    private PaymentMethod method;

    @ApiModelProperty("주문 번호")
    private String orderId;

    @ApiModelProperty("결제 금액")
    private Integer amount;

    @ApiModelProperty(value = "결제 금액 중 면세 금액", notes = "값을 넣지 않으면 기본값인 0으로 설정됨")
    private Integer taxFreeAmount;

    @ApiModelProperty("상품명")
    private String orderName;

    @ApiModelProperty("구매자 이메일")
    private String customerEmail;

    @ApiModelProperty("구매자 이름")
    private String customerName;

    @ApiModelProperty("브라우저에서 결제창이 열리는 프레임")
    private WindowTarget windowTarget;

    @Builder
    public PaymentCreateRequest(PaymentMethod method, String orderId, Integer amount, String orderName, String customerEmail, String customerName) {
        this.method = method;
        this.orderId = orderId;
        this.amount = amount;
        this.orderName = orderName;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
    }

    public Payment toEntity() {
        return Payment.builder()
                .orderId(orderId)
                .method(method)
                .amount(amount)
                .orderName(orderName)
                .customerEmail(customerEmail)
                .customerName(customerName)
                .createDate(LocalDateTime.now())
                .paySuccessYn("Y")
                .build();
    }
}
