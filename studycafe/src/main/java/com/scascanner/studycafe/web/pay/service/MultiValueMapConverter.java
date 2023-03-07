package com.scascanner.studycafe.web.pay.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiValueMapConverter {

    public static MultiValueMap<String, Object> convert(Object request) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            Map<String, Object> map = objectMapper.convertValue(request, new TypeReference<Map<String, Object>>() {
            });

            params.setAll(map);
            return params;
        } catch (Exception e) {
            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", request, e);
            throw new IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.");
        }
    }
}
