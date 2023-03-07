package com.scascanner.studycafe.web.pay.dto.kakao.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoPayRequest {

    private String cid;
    private Integer partner_order_id;
    private Integer partner_user_id;
    private String item_name;
    private Integer quantity;
    private Integer total_amount;
    private Integer tax_free_amount;
    private String approval_url;
    private String cancel_url;
    private String fail_url;

    @Builder
    public KakaoPayRequest(String cid, Integer partner_order_id, Integer partner_user_id, String item_name, Integer quantity, Integer total_amount, Integer tax_free_amount, String approval_url, String cancel_url, String fail_url) {
        this.cid = cid;
        this.partner_order_id = partner_order_id;
        this.partner_user_id = partner_user_id;
        this.item_name = item_name;
        this.quantity = quantity;
        this.total_amount = total_amount;
        this.tax_free_amount = tax_free_amount;
        this.approval_url = approval_url;
        this.cancel_url = cancel_url;
        this.fail_url = fail_url;
    }
}