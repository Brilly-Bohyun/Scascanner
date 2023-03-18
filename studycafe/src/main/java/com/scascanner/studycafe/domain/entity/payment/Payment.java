package com.scascanner.studycafe.domain.entity.payment;

import com.scascanner.studycafe.domain.entity.reservation.Reservation;

import com.scascanner.studycafe.web.pay.PaymentMethod;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private PaymentMethod method;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false)
    private String orderName;

    private String customerEmail;
    private String customerName;

    /**
     * 결제 금액, 구매자 관련 정보들을 Reservation에서 가져오도록 구현
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;    // 예약 id, 총 결제 금액을 여기서 가져오도록 설정

    @Column(nullable = false)
    private LocalDateTime createDate;    // 거래 생성 일자

    @Column(nullable = false)
    private String paymentKey;  // 추후 결제 취소 및 조회에 사용

    private String paySuccessYn;
    @Builder
    public Payment(Long id, PaymentMethod method, Integer amount, String orderId, String orderName, String customerEmail, String customerName, Reservation reservation, LocalDateTime createDate, String  paySuccessYn) {
        this.id = id;
        this.method = method;
        this.amount = amount;
        this.orderId = orderId;
        this.orderName = orderName;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.reservation = reservation;
        this.createDate = createDate;
        this.paySuccessYn = paySuccessYn;
    }
}