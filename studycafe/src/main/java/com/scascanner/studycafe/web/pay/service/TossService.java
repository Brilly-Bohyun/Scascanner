package com.scascanner.studycafe.web.pay.service;

import com.scascanner.studycafe.domain.entity.payment.Payment;
import com.scascanner.studycafe.domain.repository.PaymentRepository;
import com.scascanner.studycafe.web.pay.request.confirm.PaymentConfirmRequest;
import com.scascanner.studycafe.web.pay.request.create.PaymentCreateRequest;
import com.scascanner.studycafe.web.pay.response.confirm.PaymentSuccessConfirmResponse;

import com.scascanner.studycafe.web.pay.response.create.PaymentCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossService {

    private final PaymentRepository paymentRepository;

    /**
     * 결제 요청에 대한 검증을 처리
     * DB에 결제 정보를 저장
     * 토스페이먼츠로 결제 요청을 보내기 위한 객체를 생성 및 반환
     * @param paymentCreateRequest
     * @return
     */
    public PaymentCreateResponse create(PaymentCreateRequest paymentCreateRequest) {

        // 검증
        Integer amount = paymentCreateRequest.getAmount();
        String method = paymentCreateRequest.getMethod().name();
        String customerEmail = paymentCreateRequest.getCustomerEmail();
        String orderName = paymentCreateRequest.getOrderName();

        if (amount == null) {
            throw new IllegalArgumentException("올바르지 않은 가격입니다.");
        }

        if (!method.equals("카드")) {
            throw new IllegalArgumentException("올바르지 않은 결제 유형입니다.");
        }

        Payment payment = paymentCreateRequest.toEntity();
        paymentRepository.save(payment);

        return PaymentCreateResponse.of(payment);
    }

    public PaymentSuccessConfirmResponse confirm(PaymentConfirmRequest paymentConfirmRequest) {

        return WebClient.create(Host.CONFIRM_HOST)
                .method(HttpMethod.POST)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic " + getEncodedAuth())
                .bodyValue(paymentConfirmRequest)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus != HttpStatus.OK,
                        clientResponse -> clientResponse.createException()
                                .flatMap(it -> Mono.error(new RuntimeException("code : " + clientResponse.statusCode()))))
                .bodyToMono(PaymentSuccessConfirmResponse.class)
                .onErrorResume(throwable -> Mono.error(new RuntimeException(throwable)))
                .block();
    }

    private String getEncodedAuth() {
        return Base64.getEncoder().encodeToString((ApiKey.SECRET_KEY + ":").getBytes());
    }
}
