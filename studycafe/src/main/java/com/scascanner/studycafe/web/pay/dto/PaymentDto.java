package com.scascanner.studycafe.web.pay.dto;

import com.scascanner.studycafe.domain.entity.payment.Payment;
import com.scascanner.studycafe.domain.entity.payment.PaymentType;
import com.scascanner.studycafe.domain.entity.reservation.Reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDto {

    private Long id;

    private PaymentType paymentType;  // 결제 유형

    private Integer amount;

    private String orderId;

    private String orderName;

    private Reservation reservation;    // 예약 id, 총 결제 금액을 여기서 가져오도록 설정

    private LocalDateTime createDate;    // 거래 생성 일자

    private boolean isCompleted;    // 결제 완료 여부 (추후 열거체로 리펙토링)

}