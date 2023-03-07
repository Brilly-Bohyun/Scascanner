package com.scascanner.studycafe.web.pay.service.toss;

import com.scascanner.studycafe.web.pay.dto.toss.request.TossPayRequest;
import com.scascanner.studycafe.web.pay.dto.toss.response.TossPayResponseApproval;
import com.scascanner.studycafe.web.pay.dto.toss.response.TossPayResponseReady;
import com.scascanner.studycafe.web.pay.service.Host;
import com.scascanner.studycafe.web.pay.service.MultiValueMapConverter;
import com.scascanner.studycafe.web.pay.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class TossPaymentServiceImpl implements PaymentService<TossPayResponseApproval> {

    @Override
    public String payReady() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = getHttpHeaders();

        TossPayRequest tossPayRequest = TossPayRequest.builder()
                .orderNo("1")
                .amount(2200)
                .amountTaxFree(100)
                .productDesc("스터디룸")
                .apiKey("sk_test_w5lNQylNqa5lNQe013Nq")
                .autoExecute(true)
                .resultCallback("http://localhost:8080/tossPaySuccess")
                .retUrl("http://localhost:8080/tossPaySuccess")
                .retCancelUrl("http://localhost:8080/tossPayCancel")
                .build();

        MultiValueMap<String, Object> params = MultiValueMapConverter.convert(tossPayRequest);
        System.out.println("params = " + params);
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(params, httpHeaders);

        try {
            TossPayResponseReady tossPayResponseReady = restTemplate.postForObject(new URI(Host.TOSS_HOST + "/api/v2/payments"), body, TossPayResponseReady.class);
            log.info("TossPayResponseReady = {}", tossPayResponseReady);
            return tossPayResponseReady.getCheckoutPage();
        } catch (URISyntaxException e) {
            e.getMessage();
        }

        throw new IllegalArgumentException("잘못된 결제 정보 입니다.");
    }

    @Override
    public TossPayResponseApproval payInfo(String token) {
        return null;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        return httpHeaders;
    }
}
