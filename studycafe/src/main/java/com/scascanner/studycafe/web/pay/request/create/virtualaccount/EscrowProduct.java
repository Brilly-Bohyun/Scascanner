package com.scascanner.studycafe.web.pay.request.create.virtualaccount;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EscrowProduct {

    private String id;
    private String name;
    private String code;
    private Integer unitPrice;
    private Integer quantity;
}
