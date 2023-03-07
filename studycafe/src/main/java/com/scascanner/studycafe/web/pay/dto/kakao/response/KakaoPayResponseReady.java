package com.scascanner.studycafe.web.pay.dto.kakao.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class KakaoPayResponseReady {

    private String tid;
    private String next_redirect_pc_url;
    private LocalDate create_date;
}
