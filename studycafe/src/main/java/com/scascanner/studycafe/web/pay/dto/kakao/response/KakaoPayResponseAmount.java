package com.scascanner.studycafe.web.pay.dto.kakao.response;

import lombok.Data;

@Data
public class KakaoPayResponseAmount {
    private Integer total, tax_free, vat, point, discount;
}
