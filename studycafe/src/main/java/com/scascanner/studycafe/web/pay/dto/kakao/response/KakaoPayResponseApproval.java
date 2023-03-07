package com.scascanner.studycafe.web.pay.dto.kakao.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class KakaoPayResponseApproval {

    private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;
    private KakaoPayResponseAmount amount;
    private KakaoPayResponseCard card_info;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private LocalDate created_at, approved_at;
}
