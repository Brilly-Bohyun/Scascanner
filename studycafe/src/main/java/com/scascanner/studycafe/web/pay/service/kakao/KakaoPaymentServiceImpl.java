package com.scascanner.studycafe.web.pay.service.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scascanner.studycafe.web.pay.dto.kakao.request.KakaoPayRequest;
import com.scascanner.studycafe.web.pay.dto.kakao.response.KakaoPayResponseApproval;
import com.scascanner.studycafe.web.pay.dto.kakao.response.KakaoPayResponseReady;

import com.scascanner.studycafe.web.pay.service.Host;
import com.scascanner.studycafe.web.pay.service.MultiValueMapConverter;
import com.scascanner.studycafe.web.pay.service.PaymentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
public class KakaoPaymentServiceImpl implements PaymentService<KakaoPayResponseApproval>{

    private KakaoPayResponseReady kakaoPayResponseReady;
    private KakaoPayResponseApproval kakaoPayResponseApproval;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String payReady() {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header 정보
        HttpHeaders httpHeaders = getHttpHeaders();

        // 서버로 요청할 Body 정보
        KakaoPayRequest kakaoPayRequest = KakaoPayRequest.builder()
                .cid("TC0ONETIME")
                .partner_order_id(1)
                .partner_user_id(1)
                .item_name("스터디룸")
                .quantity(1)
                .total_amount(2200)
                .tax_free_amount(100)
                .approval_url("http://localhost:8080/kakaoPaySuccess")
                .cancel_url("http://localhost:8080/kakaoPayCancel")
                .fail_url("http://localhost:8080/kakaoPayFail")
                .build();

        MultiValueMap<String, Object> params = MultiValueMapConverter.convert(kakaoPayRequest);
        HttpEntity<MultiValueMap<String, Object>> body = new HttpEntity<>(params, httpHeaders);

        try {
            kakaoPayResponseReady = restTemplate.postForObject(new URI(Host.KAKAO_HOST + "/v1/payment/ready"), body, KakaoPayResponseReady.class);
            kakaoPayResponseReady.setCreate_date(LocalDate.now());
            System.out.println("kakaoPayResponseReady = " + kakaoPayResponseReady);
            return kakaoPayResponseReady.getNext_redirect_pc_url();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "/pay";
    }

    @Override
    public KakaoPayResponseApproval payInfo(String token) {

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header 정보
        HttpHeaders httpHeaders = getHttpHeaders();

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayResponseReady.getTid());
        params.add("partner_order_id", "1");
        params.add("partner_user_id", "1");
        params.add("pg_token", token);
        params.add("total_amount", "2200");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, httpHeaders);

        try {
            return restTemplate.postForObject(new URI(Host.KAKAO_HOST + "/v1/payment/ready"), body, KakaoPayResponseApproval.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "KakaoAK 673252fe837531c4733cc1b4ab9935fc");
        httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");

        return httpHeaders;
    }
}
